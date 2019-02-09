package com.sep.payment.paymentconcentrator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubscribeDto implements Serializable {

    private String clientId;
    private String secret;
    private String nameOfJournal;
    private String planId;

}
