package com.sep.payment.paymentconcentrator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FinishPaymentDTO {
    private String id;
    private String secret;
    private String paymentId;
    private String payerId;
}
