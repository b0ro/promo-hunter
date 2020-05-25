package org.boro.promohunter.bundle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BundleNotFoundException extends RuntimeException {

    public BundleNotFoundException(long id) {
        super(String.format("Bundle with id: %d not found", id));
    }
}
