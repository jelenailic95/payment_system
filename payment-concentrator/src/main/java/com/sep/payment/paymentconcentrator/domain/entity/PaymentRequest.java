package com.sep.payment.paymentconcentrator.domain.entity;

import com.sep.payment.paymentconcentrator.domain.TransactionStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
public class PaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double amount;

    @Column
    private String merchantId;

    @Column
    private String merchantPassword;

    @Column
    private Long merchantOrderId;

    @Column
    @CreationTimestamp
    private Date merchantTimestamp;

    @Column
    private String successUrl;

    @Column
    private String failedUrl;

    @Column
    private String errorUrl;

    @Column
    private TransactionStatus status;


    public PaymentRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(Date merchantTimestamp) {
        this.merchantTimestamp = merchantTimestamp;
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

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getFailedUrl() {
        return failedUrl;
    }

    public void setFailedUrl(String failedUrl) {
        this.failedUrl = failedUrl;
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

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
