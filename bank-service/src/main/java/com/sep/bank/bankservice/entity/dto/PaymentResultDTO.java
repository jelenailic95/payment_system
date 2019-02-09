package com.sep.bank.bankservice.entity.dto;

import com.sep.bank.bankservice.entity.TransactionStatus;

import java.util.Date;

public class PaymentResultDTO {
    private Long acquirerOrderId;
    private Date acquirerTimestamp;
    private Long issuerOrderId;
    private Date issuerTimestamp;
    private TransactionStatus status;

    public PaymentResultDTO() {
    }

    public PaymentResultDTO(Long acquirerOrderId, Date acquirerTimestamp, Long issuerOrderId, Date issuerTimestamp, TransactionStatus status) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.issuerOrderId = issuerOrderId;
        this.issuerTimestamp = issuerTimestamp;
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

    public Long getIssuerOrderId() {
        return issuerOrderId;
    }

    public void setIssuerOrderId(Long issuerOrderId) {
        this.issuerOrderId = issuerOrderId;
    }

    public Date getIssuerTimestamp() {
        return issuerTimestamp;
    }

    public void setIssuerTimestamp(Date issuerTimestamp) {
        this.issuerTimestamp = issuerTimestamp;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

}
