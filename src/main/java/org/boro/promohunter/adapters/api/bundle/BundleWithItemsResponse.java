package org.boro.promohunter.adapters.api.bundle;

import lombok.Value;
import org.boro.promohunter.adapters.api.item.ItemResponse;
import org.boro.promohunter.bundle.Bundle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Value(staticConstructor = "of")
class BundleWithItemsResponse {

    int id;
    String name;
    String description;
    BigDecimal price;
    Set<ItemResponse> items;
    LocalDateTime createdAt;
    LocalDateTime lastModifiedAt;

    static BundleWithItemsResponse of(Bundle bundle) {
        var items = bundle.getItems()
                .stream()
                .map(ItemResponse::of)
                .collect(Collectors.toSet());

        return of(bundle.getId(), bundle.getName(), bundle.getDescription(), bundle.getPrice(), items, bundle.getCreatedAt(),
                bundle.getLastModifiedAt());
    }
}
