package com.sep.cryptoservice.domain.dto;

public class BitcoinPaymentDto {

    RequestDTO requestDTO;
    PaymentRequest paymentRequest;

    public BitcoinPaymentDto() {
    }

    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public RequestDTO getRequestDTO() {
        return requestDTO;
    }

    public void setRequestDTO(RequestDTO requestDTO) {
        this.requestDTO = requestDTO;
    }
}
