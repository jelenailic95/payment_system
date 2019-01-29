package com.sep.paypal.model.dto;

import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Data
@Builder
@RequiredArgsConstructor
public class RequestPayment {

    private String clientId;
    private String clientSecret;
    private double amount;
    private String currency;
    private String nameOfJournal;
    private PaymentIntent paymentIntent;
    private PaymentMethod paymentMethod;
    private String description;

}
