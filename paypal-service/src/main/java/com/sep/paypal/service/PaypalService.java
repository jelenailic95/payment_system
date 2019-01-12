package com.sep.paypal.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.model.dto.PlanInfo;
import com.sep.paypal.model.dto.RequestCreatePlan;
import com.sep.paypal.model.entity.Seller;
import com.sep.paypal.model.enumeration.AgreementWithPayee;
import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public interface PaypalService {

    Payment executePayment(String clientId, String secret, String paymentId, String payerId) throws PayPalRESTException;

    Boolean isValidAccount(String email);

    void createPlanForSubscription(RequestCreatePlan requestCreatePlan);

    URL subscribeToPlan(String nameOfJournal, String clientId, String secret);

    void finishSubscription(String token, String clientId, String secret);

    Payment createPayment(String id, String secret, Double price, String currency, PaymentMethod paymentMethod, PaymentIntent paymentIntent, String description, String journalName, String cancelUrl, String successUrl) throws PayPalRESTException;

    void storeTransactionToDatabase(Payment payment, AgreementWithPayee agreementWithPayee);

    PlanInfo getPlanByName(String name, String clientId, String secret);

    void addNewSeller(Seller seller);

}
