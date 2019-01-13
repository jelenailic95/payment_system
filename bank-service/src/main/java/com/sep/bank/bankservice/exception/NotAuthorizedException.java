package com.sep.bank.bankservice.exception;

import lombok.Getter;

@Getter
public class NotAuthorizedException extends RuntimeException {

    private final String mail;

    public NotAuthorizedException(final String mail) {
        super("Bank user doesn not exist with this email: " + mail);
        this.mail = mail;
    }

}
