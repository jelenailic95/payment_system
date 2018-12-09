package com.sep.bank.bankservice.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.TransactionStatus;

import java.util.Date;

public class TransactionDTO {
    @JsonIgnore
    private Long id;
    private Long merchantOrderId;
    private Long paymentId;
    private TransactionStatus status;
    private Long acquirerOrderId;
    private Date acquirerTimestamp;
    private double amount;
    private String resultUrl;


    public TransactionDTO(Long merchantOrderId, Long paymentId, Long acquierOrderId, Date acquirerTimestamp, double ammount) {
        this.merchantOrderId = merchantOrderId;
        this.paymentId = paymentId;
        this.acquirerOrderId = acquierOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.amount = ammount;
    }

    public TransactionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Long getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(Long acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
}

