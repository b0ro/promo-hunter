package org.boro.promohunter.source.api;

import lombok.Value;
import org.boro.promohunter.source.Source;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
class SourceRequest {

    @NotBlank(message = "Source name must not be blank")
    @Size(min = 3, max = 255, message = "Source name must be between {min} and {max} characters long")
    String name;
    String description;

    @URL(message = "Source url must have valid url format")
    @NotBlank(message = "Source url must not be blank")
    @Size(min = 3, max = 255, message = "Source url must be between {min} and {max} characters long")
    String url;

    @NotBlank(message = "Source name selector must not be blank")
    @Size(max = 255, message = "Source name selector must be up to {max} characters long")
    String nameSelector;

    @Size(max = 255, message = "Source description selector must be up to {max} characters long")
    String descriptionSelector;

    @NotBlank(message = "Source price selector must not be blank")
    @Size(max = 255, message = "Source price selector must be up to {max} characters long")
    String priceSelector;

    static SourceRequest of(String name, String description, String url, String nameSelector,
                            String descriptionSelector, String priceSelector) {
        return new SourceRequest(name, description, url, nameSelector, descriptionSelector, priceSelector);
    }

    Source source() {
        return new Source(name, description, url, nameSelector, descriptionSelector, priceSelector);
    }
}
