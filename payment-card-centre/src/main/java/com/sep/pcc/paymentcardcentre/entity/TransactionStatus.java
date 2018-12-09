package com.sep.pcc.paymentcardcentre.entity;

public enum TransactionStatus {
    PAID,
    FAILED,      // some error
    CANCELLED,   // user pressed cancel button
    EXPIRED,     // token has expired
    REFUSED      // not enough money on the account
}
