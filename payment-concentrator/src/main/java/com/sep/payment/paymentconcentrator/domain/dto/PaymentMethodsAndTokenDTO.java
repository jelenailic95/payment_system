package com.sep.payment.paymentconcentrator.domain.dto;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
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

}
