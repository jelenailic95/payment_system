package com.sep.pcc.paymentcardcentre.entity.dto;

import com.sep.pcc.paymentcardcentre.entity.TransactionStatus;

import java.util.Date;

public class PaymentResultDTO {
    private Long acquirerOrderId;
    private Date acquirerTimestamp;
    private Long issuerOrderId;
    private Date issuerTimestamp;
    private TransactionStatus status;

    public PaymentResultDTO() {
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
