package org.boro.promohunter.bundle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.boro.jpa.AuditableEntity;
import org.boro.promohunter.item.Item;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OrderBy;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NamedEntityGraph(
        name = "bundle-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "items", subgraph = "item-sub-graph")},
        subgraphs = {
                @NamedSubgraph(
                        name = "item-sub-graph",
                        attributeNodes = {
                                @NamedAttributeNode("priceUpdates")})})
public class Bundle extends AuditableEntity {

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    public static Bundle of(String name, String description) {
        return new Bundle(name, description);
    }

    // @todo add builder after extracting entity
    public Bundle(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @OrderBy("id")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "bundle_item",
            joinColumns = @JoinColumn(name = "bundle_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items = new HashSet<>();

    public void updateFrom(Bundle bundle) {
        setName(bundle.getName());
        setDescription(bundle.getDescription());
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public BigDecimal getPrice() {
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
