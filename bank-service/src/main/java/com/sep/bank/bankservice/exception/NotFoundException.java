package com.sep.bank.bankservice.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String param;

    public NotFoundException(final String... param) {
        super(String.format("Not found entity with %s : %s", param[0], param[1]));
        this.param = param[1];
    }
}
