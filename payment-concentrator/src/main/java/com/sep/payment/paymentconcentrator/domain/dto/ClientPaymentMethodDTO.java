package com.sep.payment.paymentconcentrator.domain.dto;

public class ClientPaymentMethodDTO {
    private String clientId;

    private PaymentMethodDTO paymentMethodDTO;


    public ClientPaymentMethodDTO() {
    }

    public ClientPaymentMethodDTO(String clientId, PaymentMethodDTO paymentMethodDTO) {
        this.clientId = clientId;
        this.paymentMethodDTO = paymentMethodDTO;
    }

    public String getClientId() {
        return clientId;
    }

    public PaymentMethodDTO getPaymentMethodDTO() {
        return paymentMethodDTO;
    }

    public void setPaymentMethodDTO(PaymentMethodDTO paymentMethodDTO) {
        this.paymentMethodDTO = paymentMethodDTO;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

