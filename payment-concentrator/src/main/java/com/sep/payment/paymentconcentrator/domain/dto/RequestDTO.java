package com.sep.payment.paymentconcentrator.domain.dto;

public class RequestDTO {
    private Long clientId;
    private double amount;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}