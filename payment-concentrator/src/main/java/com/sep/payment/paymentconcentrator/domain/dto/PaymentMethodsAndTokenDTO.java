package com.sep.payment.paymentconcentrator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentMethodsAndTokenDTO {
    private Set<PaymentMethodDTO> paymentMethodDTOS;
    private String token;
    private double amount;

}
