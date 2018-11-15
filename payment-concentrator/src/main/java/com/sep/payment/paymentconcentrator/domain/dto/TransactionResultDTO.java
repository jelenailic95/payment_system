package com.sep.payment.paymentconcentrator.domain.dto;

import java.util.Date;

public class TransactionResultDTO {

    private int merchantOrderId;
    private double acquirerOrderId;
    private Date acquirerTimestamp;
    private String paymentId;
    private String status;

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public double getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(double acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
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
}
