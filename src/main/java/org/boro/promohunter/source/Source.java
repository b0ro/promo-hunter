package org.boro.promohunter.source;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.boro.jpa.AuditableEntity;
import org.boro.promohunter.item.Item;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NamedEntityGraph(
        name = "source-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "items", subgraph = "item-sub-graph")},
        subgraphs = {
                @NamedSubgraph(
                        name = "item-sub-graph",
                        attributeNodes = {
                                @NamedAttributeNode("priceUpdates")})})
public class Source extends AuditableEntity {

    @NotBlank
    @Length(min = 3, max = 255)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @URL
    @NotBlank
    @Length(min = 3, max = 255)
    private String url;

    private String nameSelector;
    private String descriptionSelector;
    private String priceSelector;

    @OrderBy("id")
    @OneToMany(mappedBy = "source")
    private Set<Item> items = new HashSet<>();

    public void updateFrom(Source source) {
        setName(source.getName());
        setDescription(source.getDescription());
        setUrl(source.getUrl());
        setNameSelector(source.getNameSelector());
        setDescriptionSelector(source.getDescriptionSelector());
        setPriceSelector(source.getPriceSelector());
    }

    public void addItem(Item item) {
        items.add(item);
        item.setSource(this);
    }
}
