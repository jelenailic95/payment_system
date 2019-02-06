package com.sep.payment.paymentconcentrator.domain.dto;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BitcoinPaymentDto {

    RequestDTO requestDTO;
    PaymentRequest paymentRequest;
}
