package org.boro.promohunter.item.api;

import lombok.Value;
import org.boro.promohunter.item.Item;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value(staticConstructor = "of")
public class ItemResponse {

    String id;

    String name;
    String description;
    String url;
    BigDecimal price;

    LocalDate createdAt;
    LocalDate lastModifiedAt;

    static ItemResponse of(Item item) {
        return of(
                String.valueOf(item.getId()),
                item.getName(),
                item.getDescription(),
                item.getUrl(),
                item.getPrice(),
                item.getCreatedAt(),
                item.getLastModifiedAt()
        );
    }
}
