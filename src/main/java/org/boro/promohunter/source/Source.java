package org.boro.promohunter.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.boro.jpa.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Source extends AuditableEntity {

    private String name;

    @Column(columnDefinition = "text")
    private String description;
    private String url;

    private String nameSelector;
    private String descriptionSelector;
    private String priceSelector;

    public void updateFrom(Source source) {
        setName(source.getName());
        setDescription(source.getDescription());
        setUrl(source.getUrl());
        setNameSelector(source.getNameSelector());
        setDescriptionSelector(source.getDescriptionSelector());
        setPriceSelector(source.getPriceSelector());
    }
}
