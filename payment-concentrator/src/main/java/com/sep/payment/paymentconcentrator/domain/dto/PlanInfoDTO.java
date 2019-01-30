package com.sep.payment.paymentconcentrator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class PlanInfoDTO {
    private String name;
    private String description;
    private String currency;
    private String amount;
    private String frequency;
    private String frequencyInterval;
}
