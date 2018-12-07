package com.sep.bank.bankservice.entity.dto;

public class PaymentDataDTO {

    private String paymentUrl;
    private String paymentId;
    private double ammount;
    private Long merchantOrderId;


    public PaymentDataDTO() {
    }

    public PaymentDataDTO(String paymentUrl, String paymentId, double ammount, Long merchant) {
        this.paymentUrl = paymentUrl;
        this.paymentId = paymentId;
        this.ammount = ammount;
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

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }
}
