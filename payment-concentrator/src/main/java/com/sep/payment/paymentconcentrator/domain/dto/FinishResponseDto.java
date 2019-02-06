package com.sep.payment.paymentconcentrator.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class FinishResponseDto {

    String username;
    // imace ili paperId ili journalId
    Long paperId;
    String journalName;
    String typeOfPayment;
    String scName;
}
