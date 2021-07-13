package org.boro.promohunter.source.api;

import lombok.Value;
import org.boro.promohunter.source.Source;

import java.time.LocalDate;

@Value
class SourceResponse {

    String id;
    String name;

    String description;
    String url;

    String nameSelector;
    String descriptionSelector;
    String priceSelector;

    LocalDate createdAt;
    LocalDate lastModifiedAt;

    static SourceResponse of(Source source) {
        return new SourceResponse(
                String.valueOf(source.getId()),
                source.getName(),
                source.getDescription(),
                source.getUrl(),
                source.getNameSelector(),
                source.getDescriptionSelector(),
                source.getPriceSelector(),
                source.getCreatedAt(),
                source.getLastModifiedAt()
        );
    }
}
