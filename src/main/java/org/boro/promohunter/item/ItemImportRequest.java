package org.boro.promohunter.item;

import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
public class ItemImportRequest {
    @URL
    @NotBlank
    private String url;
}
