package com.sep.bank.bankservice.entity.dto;

public class PaymentDataDTO {

    private String paymentUrl;
    private String paymentId;
    private double amount;
    private Long merchantOrderId;


    public PaymentDataDTO() {
    }

    public PaymentDataDTO(String paymentUrl, String paymentId, double amount, Long merchant) {
        this.paymentUrl = paymentUrl;
        this.paymentId = paymentId;
        this.amount = amount;
        this.merchantOrderId = merchant;
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
}
