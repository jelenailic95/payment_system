package com.sep.payment.paymentconcentrator.domain.dto;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;

import java.util.List;

public class PaymentMethodDetailsDTO {
    private ClientPaymentMethodDTO clientPaymentMethodDTO;
    private List<PaymentMethod> paymentMethods;

    public ClientPaymentMethodDTO getClientPaymentMethodDTO() {
        return clientPaymentMethodDTO;
    }

    public void setClientPaymentMethodDTO(ClientPaymentMethodDTO clientPaymentMethodDTO) {
        this.clientPaymentMethodDTO = clientPaymentMethodDTO;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
