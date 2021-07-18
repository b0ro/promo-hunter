package org.boro.promohunter.item.api;

import lombok.Value;
import org.boro.promohunter.item.Item;
import org.boro.promohunter.item.PriceUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Value(staticConstructor = "of")
public class FullItemResponse {

    String id;

    String name;
    String description;
    String url;
    BigDecimal price;

    Set<PriceUpdate> priceUpdates;

    LocalDate createdAt;
    LocalDate lastModifiedAt;

    static FullItemResponse of(Item item) {
        return of(
                String.valueOf(item.getId()),
                item.getName(),
                item.getDescription(),
                item.getUrl(),
                item.getPrice(),
                item.getPriceUpdates(),
                item.getCreatedAt(),
                item.getLastModifiedAt()
        );
    }
}
