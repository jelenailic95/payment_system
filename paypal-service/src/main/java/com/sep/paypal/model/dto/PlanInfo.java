package com.sep.paypal.model.dto;

import com.sep.paypal.model.enumeration.FrequencyPayment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlanInfo {
    private String name;
    private String description;
    private String currency;
    private String amount;
    private FrequencyPayment frequency;
    private String frequencyInterval;
    private String cycles;
    private String planId;
}
