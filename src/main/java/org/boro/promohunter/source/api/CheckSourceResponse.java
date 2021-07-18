package org.boro.promohunter.source.api;

import lombok.Value;
import org.boro.promohunter.item.Item;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
class CheckSourceResponse {

    String name;
    String description;
    String url;
    BigDecimal price;

    static CheckSourceResponse of(Item item) {
        return of(item.getName(), item.getDescription(), item.getUrl(), item.getPrice());
    }
}
