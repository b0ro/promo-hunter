package org.boro.promohunter.adapters.api.item.dto;

import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Value(staticConstructor = "of")
public class ItemImportRequest {
    @URL
    @NotBlank
    String url;
}
