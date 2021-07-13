package org.boro.promohunter.item.api;

import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
class ItemImportRequest {

    @URL(message = "Item url must have valid url format")
    @NotBlank(message = "Item url must not be blank")
    @Size(min = 3, max = 255, message = "Item url must be between {min} and {max} characters long")
    String url;

    static ItemImportRequest of(String url) {
        return new ItemImportRequest(url);
    }
}
