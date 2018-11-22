package com.sep.paypal.model;

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
