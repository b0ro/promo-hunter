package org.boro.promohunter.item;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoSourceForItemUrlFoundException extends RuntimeException {

    public NoSourceForItemUrlFoundException(String url, Throwable cause) {
        super(String.format("No source for item url %s found", url), cause);
    }
}
