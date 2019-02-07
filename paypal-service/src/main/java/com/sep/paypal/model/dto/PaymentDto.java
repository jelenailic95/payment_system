package com.sep.paypal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


public class PaymentDto {

    RequestPayment requestDTO;
    // upisano u koncentratoru
    PaymentRequestDto paymentRequest;

    PaymentDto(){}

    public RequestPayment getRequestDTO() {
        return requestDTO;
    }

    public void setRequestDTO(RequestPayment requestDTO) {
        this.requestDTO = requestDTO;
    }

    public PaymentRequestDto getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(PaymentRequestDto paymentRequest) {
        this.paymentRequest = paymentRequest;
    }
}
