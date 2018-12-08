package com.sep.paypal.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.model.RequestCreatePlan;
import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public interface PaypalService {

    Payment createPayment(Double total, String currency, PaymentMethod method, PaymentIntent intent,
                                 String description, String cancelUrl, String successUrl) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;


    Boolean isValidAccount(String email);

    void createPlanForSubscription(RequestCreatePlan requestCreatePlan);

    URL subscribeToPlan(String nameOfJournal);

    void finishSubscription(String token);
}
