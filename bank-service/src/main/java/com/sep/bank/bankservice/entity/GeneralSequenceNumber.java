package com.sep.bank.bankservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class GeneralSequenceNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(9999999999L)
    @Min(1000000000L)
    private Long paymentCounter;

    @Max(9999999999L)
    @Min(1000000000L)
    private Long acquirerCounter;

    @Max(9999999999L)
    @Min(1000000000L)
    private Long issuerCounter;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAcquirerCounter() {
        return acquirerCounter;
    }

    public void setAcquirerCounter(Long acquirerCounter) {
        this.acquirerCounter = acquirerCounter;
    }

    public Long getIssuerCounter() {
        return issuerCounter;
    }

    public void setIssuerCounter(Long issuerCounter) {
        this.issuerCounter = issuerCounter;
    }

    public Long getPaymentCounter() {
        return paymentCounter;
    }

    public void setPaymentCounter(Long paymentCounter) {
        this.paymentCounter = paymentCounter;
    }
}
