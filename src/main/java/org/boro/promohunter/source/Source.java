package org.boro.promohunter.source;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.boro.jpa.AuditableEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
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

    public void updateFrom(Source source) {
        setName(source.getName());
        setDescription(source.getDescription());
        setUrl(source.getUrl());
        setNameSelector(source.getNameSelector());
        setDescriptionSelector(source.getDescriptionSelector());
        setPriceSelector(source.getPriceSelector());
    }
}
