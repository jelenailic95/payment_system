package com.sep.paypal.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.model.PaymentIntent;
import com.sep.paypal.model.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public interface PaypalService {

    Payment createPayment(Double total, String currency, PaymentMethod method, PaymentIntent intent,
                                 String description, String cancelUrl, String successUrl) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;


}
