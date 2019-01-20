package com.sep.paypal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SellerDto {

    private String clientId;
    private String secret;
    private String journalName;
    private String journalMail;
}
