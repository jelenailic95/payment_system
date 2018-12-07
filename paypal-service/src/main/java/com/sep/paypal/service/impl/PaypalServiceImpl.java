package com.sep.paypal.service.impl;

import com.paypal.api.payments.Currency;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.svcs.services.AdaptiveAccountsService;
import com.paypal.svcs.types.aa.AccountIdentifierType;
import com.paypal.svcs.types.aa.GetVerifiedStatusRequest;
import com.paypal.svcs.types.aa.GetVerifiedStatusResponse;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.sep.paypal.config.AdaptiveConfiguration;
import com.sep.paypal.model.JournalPlan;
import com.sep.paypal.model.RequestCreatePlan;
import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
import com.sep.paypal.repository.JournalPlanRepository;
import com.sep.paypal.service.PaypalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaypalServiceImpl implements PaypalService {

    private Logger logger = LoggerFactory.getLogger(PaypalService.class);

    private final APIContext apiContext;

    private final JournalPlanRepository journalPlanRepository;

    @Autowired
    public PaypalServiceImpl(APIContext apiContext, JournalPlanRepository journalPlanRepository) {
        this.apiContext = apiContext;
        this.journalPlanRepository = journalPlanRepository;
    }

    @Override
    public Payment createPayment(Double total, String currency, PaymentMethod method, PaymentIntent intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.valueOf(total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());
        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    @Override
    public Boolean isValidAccount(String email) {
        Map<String, String> configurationMap = AdaptiveConfiguration.getAcctAndConfig();
        AdaptiveAccountsService service = new AdaptiveAccountsService(configurationMap);
        RequestEnvelope requestEnvelope = new RequestEnvelope();
        requestEnvelope.setErrorLanguage("en_US");
        GetVerifiedStatusRequest req = new GetVerifiedStatusRequest(
                requestEnvelope, "NONE");
        AccountIdentifierType accountIdentifier = new AccountIdentifierType();
        accountIdentifier.setEmailAddress(email);
        req.setAccountIdentifier(accountIdentifier);
        GetVerifiedStatusResponse resp = new GetVerifiedStatusResponse();
        try {
            resp = service.getVerifiedStatus(req);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return resp.getAccountStatus() != null;
    }

    @Override
    public void createPlanForSubscription(RequestCreatePlan request) {
        Plan plan = new Plan();
        plan.setName(request.getNameOfJournal());
        plan.setDescription(request.getDescription());
        plan.setType("INFINITE");

        PaymentDefinition paymentDefinition = new PaymentDefinition();
        paymentDefinition.setName(String.format("%s payments", request.getTypeOfPlan().name()));
        paymentDefinition.setType(request.getTypeOfPlan().name());
        paymentDefinition.setFrequency(request.getFrequencyPayment().name());
        paymentDefinition.setFrequencyInterval(String.valueOf(request.getFrequencyInterval()));
        paymentDefinition.setCycles(String.valueOf(request.getCycles()));

        Currency currency = new Currency();
        currency.setCurrency(request.getCurrency());
        currency.setValue(String.valueOf(request.getPrice()));
        paymentDefinition.setAmount(currency);

        List<PaymentDefinition> paymentDefinitionList = new ArrayList<>();
        paymentDefinitionList.add(paymentDefinition);
        plan.setPaymentDefinitions(paymentDefinitionList);

        MerchantPreferences merchantPreferences = new MerchantPreferences();
        merchantPreferences.setSetupFee(currency);
        merchantPreferences.setCancelUrl("https://example.com/cancel");
        merchantPreferences.setReturnUrl("http://localhost:8762/paypal/plan/finishSubscription");
        merchantPreferences.setMaxFailAttempts("0");
        merchantPreferences.setAutoBillAmount("YES");
        merchantPreferences.setInitialFailAmountAction("CONTINUE");
        plan.setMerchantPreferences(merchantPreferences);

        activatePlan(plan, request.getNameOfJournal());
    }

    private void activatePlan(Plan plan, String nameOfJournal) {
        try {
            Plan createdPlan = plan.create(apiContext);
            logger.info("Created plan with id = {}", createdPlan.getId());
            logger.info("Plan state = {}", createdPlan.getState());
            // Set up plan activate PATCH request
            List<Patch> patchRequestList = new ArrayList<>();
            Map<String, String> value = new HashMap<>();
            value.put("state", "ACTIVE");

            // Create update object to activate plan
            Patch patch = new Patch();
            patch.setPath("/");
            patch.setValue(value);
            patch.setOp("replace");
            patchRequestList.add(patch);

            // Activate plan
            createdPlan.update(apiContext, patchRequestList);
            JournalPlan journalPlan;
            journalPlan = JournalPlan.builder().journal(nameOfJournal).planId(createdPlan.getId()).build();
            journalPlanRepository.save(journalPlan);

        } catch (PayPalRESTException e) {
            logger.error(e.getDetails().getMessage());
        }
    }

    @Override
    public URL subscribeToPlan(String nameOfJournal) {
        Agreement agreement = new Agreement();
        agreement.setName(String.format("Subscription for %s", nameOfJournal));
        agreement.setDescription("Basic Agreement");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        // Add 30 seconds to make sure Paypal accept the agreement date
        Date rightNow = new Date(new Date().getTime() + 30000);
        agreement.setStartDate(df.format(rightNow));

        Plan plan = new Plan();
        plan.setId(journalPlanRepository.findJournalPlanByJournal(nameOfJournal).getPlanId());
        agreement.setPlan(plan);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        agreement.setPayer(payer);

        try {
            agreement = agreement.create(apiContext);

            for (Links links : agreement.getLinks()) {
                if ("approval_url".equals(links.getRel())) {
                    return new URL(links.getHref());
                    //REDIRECT USER TO url
                }
            }
        } catch (UnsupportedEncodingException | PayPalRESTException | MalformedURLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    @Override
    public void finishSubscription(String token) {
        Agreement agreement = new Agreement();
        agreement.setToken(token);

        try {
            Agreement activeAgreement = agreement.execute(apiContext, agreement.getToken());
            logger.info("Agreement created with ID {}", activeAgreement.getId());
        } catch (PayPalRESTException e) {
            logger.error(e.getDetails().getMessage());
        }
    }
}
