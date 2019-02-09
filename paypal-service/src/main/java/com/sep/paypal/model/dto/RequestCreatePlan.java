package com.sep.paypal.model.dto;

import com.sep.paypal.model.enumeration.FrequencyPayment;
import com.sep.paypal.model.enumeration.PaymentTypePlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Data
@Builder
@RequiredArgsConstructor
public class RequestCreatePlan {

    private String clientId;
    private String clientSecret;
    private Double price;
    private String currency;
    private String nameOfJournal;
    private String description;
    private PaymentTypePlan typeOfPlan;
    private FrequencyPayment frequencyPayment;
    // E.G:
    // interv=2, type: MONTH, cycles: 3
    // JAN, MAR, JUN (cycles = months, interval = between months)
    @Max(value = 12)
    @Min(value = 1)
    private int frequencyInterval;
    @Min(value = 1)
    @Max(value = 12)
    private int cycles;


}
