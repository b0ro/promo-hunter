package org.boro.promohunter.adapters.api.bundle.dto;

import lombok.Builder;
import lombok.Value;
import org.boro.promohunter.bundle.Bundle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
public class BundleRequest {

    @NotBlank(message = "Bundle name must not be blank")
    @Size(min = 3, max = 255, message = "Bundle name must be between {min} and {max} characters long")
    String name;
    String description;

    public static BundleRequest of(String name, String description) {
        return new BundleRequest(name, description);
    }

    public Bundle bundle() {
        return Bundle.of(name, description);
    }
}
