package org.boro.promohunter.adapters.api.bundle;

import lombok.Value;
import org.boro.promohunter.bundle.Bundle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
class BundleRequest {

    @NotBlank(message = "Bundle name must not be blank")
    @Size(min = 3, max = 255, message = "Bundle name must be between {min} and {max} characters long")
    String name;
    String description;

    Bundle asBundle() {
        return Bundle.of(name, description);
    }
}
