package org.boro.promohunter.adapters.api.item;

import lombok.Value;
import org.boro.promohunter.item.Item;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Value(staticConstructor = "of")
public class ItemResponse {

    String name;
    String description;
    String url;
    BigDecimal price;
    Set<PriceUpdateResponse> priceUpdates;

    public static ItemResponse of (Item item) {
        var priceUpdates = item.getPriceUpdates()
                .stream()
                .map(PriceUpdateResponse::of)
                .collect(Collectors.toSet());

        return of(item.getName(), item.getDescription(), item.getUrl(), item.getPrice(), priceUpdates);
    }
}
