package com.sep.bank.bankservice.entity.dto;

import java.util.Date;

public class TransactionDTO {

    private int merchantOrderId;
    private String paymentId;
    private String status;
    private int acquierOrderId;
    private Date acquirerTimestamp;

    public TransactionDTO(int merchantOrderId, String paymentId, int acquierOrderId, Date acquirerTimestamp) {
        this.merchantOrderId = merchantOrderId;
        this.paymentId = paymentId;
        this.acquierOrderId = acquierOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public TransactionDTO() {
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAcquierOrderId() {
        return acquierOrderId;
    }

    public void setAcquierOrderId(int acquierOrderId) {
        this.acquierOrderId = acquierOrderId;
    }

    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }
}

