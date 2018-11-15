package com.sep.payment.paymentconcentrator.domain.dto;

public class PaymentDataDTO {

    private String paymentUrl;
    private String paymentId;

    public PaymentDataDTO() {
    }

    public PaymentDataDTO(String paymentUrl, String paymentId) {
        this.paymentUrl = paymentUrl;
        this.paymentId = paymentId;
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
}
