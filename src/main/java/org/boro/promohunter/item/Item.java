package org.boro.promohunter.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.boro.jpa.AuditableEntity;
import org.boro.promohunter.source.Source;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank
    @Length(min = 3, max = 255)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @URL
    @NotBlank
    @Length(min = 3, max = 255)
    private String url;

    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 13, fraction = 4)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnore
    @BatchSize(size = 100)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @OrderBy("id")
    @BatchSize(size = 100)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "item_id")
    @OneToMany
    private Set<PriceUpdate> priceUpdates = new HashSet<>();

    public Item(int id) {
        this.id = id;
    }

    public Item(String url, Source source) {
        this.url = url;
        this.source = source;
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
