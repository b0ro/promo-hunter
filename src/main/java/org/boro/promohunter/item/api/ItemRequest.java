package org.boro.promohunter.item.api;

import lombok.Value;
import org.boro.promohunter.item.Item;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Value(staticConstructor = "of")
class ItemRequest {

    @NotBlank(message = "Item name must not be blank")
    @Size(min = 3, max = 255, message = "Item name must be between {min} and {max} characters long")
    String name;

    String description;

    @URL(message = "Item url must have valid url format")
    @NotBlank(message = "Item url must not be blank")
    @Size(min = 3, max = 255, message = "Item url must be between {min} and {max} characters long")
    String url;

    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 13, fraction = 4)
    BigDecimal price;

    Item item() {
        return new Item(name, description, url, price);
    }
}
