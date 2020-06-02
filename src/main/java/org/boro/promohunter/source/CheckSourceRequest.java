package org.boro.promohunter.source;

import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class CheckSourceRequest {
    @NotNull
    Source source;

    @URL
    @NotBlank
    String url;
}
