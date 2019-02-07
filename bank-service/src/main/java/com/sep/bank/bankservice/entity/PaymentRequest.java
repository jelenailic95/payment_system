package com.sep.bank.bankservice.entity;

import javax.persistence.*;

@Entity
public class PaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double amount;

    @Column
    private Long merchantAccountId;

    @Column
    private Long merchantOrderId;

    @Lob
    private String paymentUrl;

    @Column
    private String paymentId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getMerchantAccountId() {
        return merchantAccountId;
    }

    public void setMerchantAccountId(Long merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentRequest() {

    }

    public PaymentRequest(double amount, Long merchantAccountId, Long merchantOrderId, String paymentUrl,
                          String paymentId) {
        this.amount = amount;
        this.merchantAccountId = merchantAccountId;
        this.merchantOrderId = merchantOrderId;
        this.paymentUrl = paymentUrl;
        this.paymentId = paymentId;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "id=" + id +
                ", amount=" + amount +
                ", merchantAccountId=" + merchantAccountId +
                ", merchantOrderId=" + merchantOrderId +
                ", paymentUrl='" + paymentUrl + '\'' +
                ", paymentId='" + paymentId + '\'' +
                '}';
    }
}
