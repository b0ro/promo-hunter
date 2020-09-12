package org.boro.promohunter.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.boro.jpa.AuditableEntity;
import org.boro.promohunter.source.Source;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@NamedEntityGraph(
        name = "item-graph",
        attributeNodes = {
                @NamedAttributeNode("priceUpdates")})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Item extends AuditableEntity {

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private String url;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @OrderBy("id")
    @JoinColumn(name = "item_id")
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<PriceUpdate> priceUpdates = new HashSet<>();

    public static Item of(int id, String name, String description) {
        return new Item(id, name, description);
    }

    public static Item of(String name, Source source) {
        return new Item(name, source);
    }

    public static Item of(String name, String description, String url, BigDecimal price) {
        return new Item(name, description, url, price);
    }

    public static Item of(int id, String name, String description, String url, BigDecimal price) {
        return new Item(id, name, description, url, price);
    }

    Item(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    Item(String url, Source source) {
        this.url = url;
        this.source = source;
    }

    Item(String name, String description, String url, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.url = url;
        setPrice(price);
    }

    Item(int id, String name, String description, String url, BigDecimal price) {
        this(name, description, url, price);
        this.id = id;
    }

    public void updateFrom(Item item) {
        setName(item.getName());
        setDescription(item.getDescription());
        setUrl(item.getUrl());
        setPrice(item.price);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        priceUpdates.add(new PriceUpdate(price));
    }
}
