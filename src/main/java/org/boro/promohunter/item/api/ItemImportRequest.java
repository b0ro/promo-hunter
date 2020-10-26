package org.boro.promohunter.item.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
class ItemImportRequest {

    @URL(message = "Item url must have valid url format")
    @NotEmpty(message = "Item url must not be empty")
    @Size(max = 255, message = "Item url must be between {min} and {max} characters long")
    String url;

    @JsonCreator
    static ItemImportRequest of(String url) {
        return new ItemImportRequest(url);
    }
}
