package org.boro.promohunter.adapters.api.item;

import lombok.Value;
import org.boro.promohunter.item.PriceUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value(staticConstructor = "of")
public class PriceUpdateResponse {

    BigDecimal value;
    LocalDate createdAt;

    public static PriceUpdateResponse of(PriceUpdate priceUpdate) {
        return of(priceUpdate.getValue(), priceUpdate.getCreatedAt().toLocalDate());
    }
}
