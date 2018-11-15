package com.sep.payment.paymentconcentrator.domain.dto;

import java.util.Date;

public class TransactionResultCustomerDTO {
    private double merchantOrderId;
    private double acquirerOrderId;
    private Date acquirerTimestamp;
    private String paymentId;
    private String resultUrl;

    public TransactionResultCustomerDTO(double merchantOrderId, double acquirerOrderId, Date acquirerTimestamp,
                                        String paymentId, String resultUrl) {
        this.merchantOrderId = merchantOrderId;
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.paymentId = paymentId;
        this.resultUrl = resultUrl;
    }

    public double getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(double merchantOrderId) {
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

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
}
