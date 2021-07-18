package org.boro.promohunter.source.api;

import lombok.Value;
import org.boro.promohunter.source.Source;
import org.boro.promohunter.source.validation.Selector;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value(staticConstructor = "of")
class SourceRequest {

    @NotBlank(message = "Source name must not be blank")
    @Size(max = 255, message = "Source name must be up to {max} characters long")
    String name;
    String description;

    @NotEmpty(message = "Source url must not be empty")
    @URL(message = "Source url must have valid url format")
    @Size(max = 255, message = "Source url must be up to {max} characters long")
    String url;

    @NotEmpty(message = "Source name selector must not be empty")
    @Size(max = 255, message = "Source name selector must be up to {max} characters long")
    @Selector(message = "Name selector must be valid CSS selector")
    String nameSelector;

    @Size(max = 255, message = "Source description selector must be up to {max} characters long")
    @Selector(message = "Description selector must be valid CSS selector")
    String descriptionSelector;

    @NotEmpty(message = "Source price selector must not be empty")
    @Size(max = 255, message = "Source price selector must be up to {max} characters long")
    @Selector(message = "Price selector must be valid CSS selector")
    String priceSelector;

    Source source() {
        return new Source(name, description, url, nameSelector, descriptionSelector, priceSelector);
    }
}
