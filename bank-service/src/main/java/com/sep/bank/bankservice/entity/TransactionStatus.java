package com.sep.bank.bankservice.entity;

import java.io.Serializable;

public enum TransactionStatus implements Serializable {
    PAID,
    FAILED,      // some error
    CANCELLED,   // user pressed cancel button
    EXPIRED,     // token has expired
    REFUSED      // not enough money on the account
}
