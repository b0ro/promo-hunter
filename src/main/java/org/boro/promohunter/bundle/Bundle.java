package org.boro.promohunter.bundle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.boro.jpa.AuditableEntity;
import org.boro.promohunter.item.Item;
import org.boro.promohunter.item.PriceUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents named set of items, returns their summarized price and price updates
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NamedEntityGraph(
        name = "bundle-graph",
        attributeNodes = {@NamedAttributeNode(value = "items", subgraph = "item-sub-graph")},
        subgraphs = {@NamedSubgraph(
                name = "item-sub-graph",
                attributeNodes = {@NamedAttributeNode("priceUpdates")})})
public class Bundle extends AuditableEntity {

    private static final Comparator<PriceUpdate> PRICE_UPDATE_COMPARATOR =
            Comparator.comparing(PriceUpdate::getCreatedAt);
    private static final Random RANDOM_GENERATOR = new Random();

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OrderBy("id")
    @ManyToMany
    @JoinTable(
            name = "bundle_item",
            joinColumns = @JoinColumn(name = "bundle_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private Set<Item> items = new HashSet<>();

    public Bundle(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Bundle(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateFrom(Bundle bundle) {
        setName(bundle.getName());
        setDescription(bundle.getDescription());
    }

    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Get bundle price
     * <p>
     * Bundle price is sum of all item prices
     *
     * @return return bundle price
     */
    public BigDecimal getPrice() {
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<PriceUpdate> getPriceUpdates() {
        var priceUpdatesPerDay = getItemPriceUpdatesPerDay();
        if (priceUpdatesPerDay.isEmpty()) {
            return Set.of();
        }

        // get prices for the first day
        var currentPriceUpdates = priceUpdatesPerDay.firstEntry().getValue();
        var priceUpdates = new TreeSet<>(PRICE_UPDATE_COMPARATOR);
        var lastUpdateDate = priceUpdatesPerDay.lastKey();

        priceUpdatesPerDay.forEach((createdAt, priceUpdatesForDay) -> {
            // update changed prices
            if (currentPriceUpdates != priceUpdatesForDay) {
                // replace previous price with the new one for given item
                priceUpdatesForDay.forEach(currentPriceUpdates::put);
            }
            // calculate sum of all prices
            var price = currentPriceUpdates.values()
                    .stream()
                    .map(ItemAwarePriceUpdate::getValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // filter price updates older than creation of the bundle, can happen when older item is assigned to
            // another bundle
            if (isDateBeforeBundleWasCreated(createdAt, priceUpdatesPerDay, lastUpdateDate)) {
                return;
            }

            // skip if price is the same as the one for previous date
            if (priceDidntChange(price, priceUpdates)) {
                return;
            }

            // if price update is before bundle was created
            createdAt = createdAt.isBefore(this.createdAt) ? this.createdAt : createdAt;
            priceUpdates.add(new PriceUpdate(generateUniqueItemId(), price, createdAt));
        });

        return priceUpdates;
    }

    private int generateUniqueItemId() {
        return RANDOM_GENERATOR.ints(1, Integer.MIN_VALUE, -1)
                .findFirst()
                .orElse(0);
    }

    private boolean priceDidntChange(BigDecimal price, TreeSet<PriceUpdate> priceUpdates) {
        return !priceUpdates.isEmpty() && price.compareTo(priceUpdates.last().getValue()) == 0;
    }

    private boolean isDateBeforeBundleWasCreated(
            LocalDate createdAt,
            TreeMap<LocalDate, Map<Integer, ItemAwarePriceUpdate>> priceUpdatesPerDay,
            LocalDate lastUpdateDate
    ) {
        return createdAt.isBefore(this.createdAt) && !createdAt.equals(lastUpdateDate)
                && Objects.nonNull(priceUpdatesPerDay.higherKey(createdAt))
                && !priceUpdatesPerDay.higherKey(createdAt).isAfter(this.createdAt);
    }

    /**
     * Return all bundle item price updates grouped by date
     * <p>
     * Returned DTOs additionally contains item id so they can be processed later on
     *
     * @return item price updates per day
     */
    private TreeMap<LocalDate, Map<Integer, ItemAwarePriceUpdate>> getItemPriceUpdatesPerDay() {
        return items.stream()
                .flatMap(item -> item.getPriceUpdates()
                        .stream()
                        // add info about item to each price update
                        .map(priceUpdate -> ItemAwarePriceUpdate.of(item, priceUpdate))
                        .collect(Collectors.toList())
                        .stream()
                )
                // group price updates by dates...
                .collect(Collectors.groupingBy(
                        ItemAwarePriceUpdate::getCreatedAt,
                        TreeMap::new,
                        // and their item ids
                        Collectors.toMap(
                                ItemAwarePriceUpdate::getItemId,
                                Function.identity()
                        )
                ));
    }

    @Value
    private static class ItemAwarePriceUpdate {
        Integer itemId;
        BigDecimal value;
        LocalDate createdAt;

        public static ItemAwarePriceUpdate of(Item item, PriceUpdate priceUpdate) {
            return new ItemAwarePriceUpdate(
                    item.getId(),
                    priceUpdate.getValue(),
                    priceUpdate.getCreatedAt()
            );
        }
    }
}
