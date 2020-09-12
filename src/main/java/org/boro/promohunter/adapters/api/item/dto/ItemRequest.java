package org.boro.promohunter.adapters.api.item.dto;

import lombok.Builder;
import lombok.Value;
import org.boro.promohunter.item.Item;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@Builder
public class ItemRequest {

    @NotBlank
    @Length(min = 3, max = 255)
    String name;

    String description;

    @URL
    @NotBlank
    @Length(min = 3, max = 255)
    String url;

    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 13, fraction = 4)
    BigDecimal price;

    public static ItemRequest of(String name, String description, String url, BigDecimal price) {
        return new ItemRequest(name, description, url, price);
    }

    public Item item() {
        return Item.of(name, description, url, price);
    }
}
