package com.sep.scientificcentre.scientificcentre.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FinishPaymentDto {

    String username;
    // imace ili paperId ili journalId
    Long paperId;
    String journalName;
    String typeOfPayment;
}
