package com.sep.payment.paymentconcentrator.domain.dto;

import java.util.Date;

public class PaymentRequestDTO {
    private double amount;
    private String merchantId;
    private String merchantPassword;
    private double merchantOrderId;
    private Date merchantTimestamp;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public double getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(double merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Date getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(Date merchantTimestamp) {
        this.merchantTimestamp = merchantTimestamp;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFaildUrl() {
        return failedUrl;
    }

    public void setFaildUrl(String faildUrl) {
        this.failedUrl = faildUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }
}

