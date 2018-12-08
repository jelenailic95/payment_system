package com.sep.paypal.model;

import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@RequiredArgsConstructor
public class RequestPayment {

    private Double price;
    private String currency;
    private String nameOfJournal;
    private PaymentIntent paymentIntent;
    private PaymentMethod paymentMethod;
    private String description;



}
