package com.sep.cryptoservice.domain.dto;

import javax.persistence.Column;

public class RequestDTO {
    //kome se placa (njegovi podaci se traze u bazi
    private String client;
    // kod bitcoina token
    private String clientId;
    @Column(precision=10, scale=2)
    private double amount;
    private String currency;


    public RequestDTO() {
    }

    public RequestDTO(String client, String clientId, double amount) {
        this.client = client;
        this.clientId = clientId;
        this.amount = amount;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
