package com.sep.payment.paymentconcentrator.exception;

import lombok.Getter;

@Getter
public class NotAuthorizedException extends RuntimeException {

    private final String mail;

    public NotAuthorizedException(final String mail) {
        super("Paypal user not exists with email: " + mail);
        this.mail = mail;
    }

}
