package org.boro.promohunter.adapters.api.bundle;

import lombok.Value;
import org.boro.promohunter.bundle.Bundle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value(staticConstructor = "of")
class BundleResponse {

    int id;
    String name;
    String description;
    BigDecimal price;
    LocalDateTime createdAt;
    LocalDateTime lastModifiedAt;

    public static BundleResponse of(Bundle bundle) {
        return of(bundle.getId(), bundle.getName(), bundle.getDescription(), bundle.getPrice(), bundle.getCreatedAt(),
                bundle.getLastModifiedAt());
    }
}
