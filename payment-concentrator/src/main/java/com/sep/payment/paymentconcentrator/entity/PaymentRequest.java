package com.sep.payment.paymentconcentrator.entity;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

public class PaymentRequest {

    @Size(max = 30, min=30)
    private String merchantId;

    @Size(max = 100, min=100)
    private String merchantPassword;

    private double amount;

    @Max(10)
    @Min(10)
    private double merchantOrderId;

    private Date merchandTimestamp;

    private String successUrl;
    private String faildUrl;
    private String errorUrl;


    public PaymentRequest() {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(double merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Date getMerchandTimestamp() {
        return merchandTimestamp;
    }

    public void setMerchandTimestamp(Date merchandTimestamp) {
        this.merchandTimestamp = merchandTimestamp;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFaildUrl() {
        return faildUrl;
    }

    public void setFaildUrl(String faildUrl) {
        this.faildUrl = faildUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }
}
