package org.boro.promohunter.source;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SourceNotFoundException extends RuntimeException {

    public SourceNotFoundException(String message) {
        super(message);
    }

    public static SourceNotFoundException withId(long id) {
        return new SourceNotFoundException(String.format("Source with id: %d not found", id));
    }

    public static SourceNotFoundException withUrl(String url) {
        return new SourceNotFoundException(String.format("Source with url: %s not found", url));
    }
}
