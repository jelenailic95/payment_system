package com.sep.paypal.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {

    private final String param;

    public NotFoundException(final String... param) {
        super(String.format("Not found entity with %s : %s", param[0], param[1]));
        this.param = param[1];
    }
}
