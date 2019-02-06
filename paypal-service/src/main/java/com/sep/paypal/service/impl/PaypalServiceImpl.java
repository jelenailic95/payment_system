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
import com.sep.paypal.exception.NotFoundException;
import com.sep.paypal.model.dto.PlanInfo;
import com.sep.paypal.model.dto.RequestCreatePlan;
import com.sep.paypal.model.entity.JournalPlan;
import com.sep.paypal.model.entity.Seller;
import com.sep.paypal.model.entity.TransactionPayment;
import com.sep.paypal.model.enumeration.*;
import com.sep.paypal.repository.JournalPlanRepository;
import com.sep.paypal.repository.SellerRepository;
import com.sep.paypal.repository.TransactionRepository;
import com.sep.paypal.service.PaypalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaypalServiceImpl implements PaypalService {

    private Logger logger = LoggerFactory.getLogger(PaypalService.class);

    private APIContext apiContext;

    private final JournalPlanRepository journalPlanRepository;

    private final TransactionRepository transactionRepository;

    private final SellerRepository sellerRepository;

    @Value("${paypal.mode}")
    private String mode;

    @Value("${client.host}")
    private String host;

    @Autowired
    public PaypalServiceImpl(APIContext apiContext, JournalPlanRepository journalPlanRepository, TransactionRepository transactionRepository, SellerRepository sellerRepository) {
        this.apiContext = apiContext;
        this.journalPlanRepository = journalPlanRepository;
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Payment createPayment(String id, String secret, Double total, String currency,
                                 PaymentMethod method, PaymentIntent intent, String description,
                                 String journalName, String cancelUrl, String successUrl) throws PayPalRESTException {
        Details details = new Details();
        details.setShipping("0");

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.valueOf(total));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        Item item = new Item();
        item.setName(journalName).setQuantity("1").setCurrency(currency).setPrice(String.valueOf(total));
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);


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

        return payment.create(new APIContext(id, secret, mode));

    }

    @Override
    public Payment executePayment(String id, String secret, String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        Payment result = payment.execute(new APIContext(id, secret, mode), paymentExecute);
        this.storeTransactionToDatabase(result, null);
        return result;
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
        Seller seller = this.sellerRepository.findSellerByClientIdAndSecret(
                request.getClientId(), request.getClientSecret());
        if (seller == null) {
            throw new NotFoundException("name and email", request.getNameOfJournal());
        }
        plan.setName(request.getNameOfJournal());
        plan.setDescription(request.getDescription());
        plan.setType("FIXED");


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
        merchantPreferences.setCancelUrl(host + "/cancel");
        String successUrl = host + "/result/success";
        successUrl = successUrl.concat("?id=").concat(request.getClientId());
        successUrl = successUrl.concat("&secret=").concat(request.getClientSecret());
        successUrl = successUrl.concat("&subscription=").concat("true");

        merchantPreferences.setReturnUrl(successUrl);
        merchantPreferences.setMaxFailAttempts("0");
        merchantPreferences.setAutoBillAmount("YES");
        merchantPreferences.setInitialFailAmountAction("CONTINUE");
        plan.setMerchantPreferences(merchantPreferences);
        activatePlan(plan, request.getNameOfJournal(), request.getClientId(), request.getClientSecret());
    }

    private void activatePlan(Plan plan, String nameOfJournal, String clientId, String secret) {
        try {
            APIContext apiContext = new APIContext(clientId, secret, mode);
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
            Seller seller = this.sellerRepository.findSellerByClientIdAndSecret(clientId, secret);
            JournalPlan journalPlan;
            journalPlan = JournalPlan.builder().journal(nameOfJournal)
                    .planId(createdPlan.getId())
                    .payee(seller.getJournalMail()).build();
            journalPlanRepository.save(journalPlan);

        } catch (PayPalRESTException e) {
            logger.error(e.getDetails().getMessage());
        }
    }

    @Override
    public URL subscribeToPlan(String nameOfJournal, String clientId, String secret, String planId) {
        Agreement agreement = new Agreement();
        agreement.setName(String.format("Subscription for %s", nameOfJournal));
        agreement.setDescription(nameOfJournal);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        // Add 30 seconds to make sure Paypal accept the agreement date
        Date rightNow = new Date(new Date().getTime() + 30000);
        agreement.setStartDate(df.format(rightNow));

        Plan plan = new Plan();
        JournalPlan journalPlan = journalPlanRepository.findJournalPlanByPlanId(planId);
        if (journalPlan == null) {
            throw new NotFoundException("name", nameOfJournal);
        }
        plan.setId(journalPlan.getPlanId());
        agreement.setPlan(plan);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        agreement.setPayer(payer);

        try {
            agreement = agreement.create(new APIContext(clientId, secret, mode));

            for (Links links : agreement.getLinks()) {
                if ("approval_url".equals(links.getRel())) {
                    return new URL(links.getHref().concat("&planId=").concat(journalPlan.getPlanId()));
                    //REDIRECT USER TO url
                }
            }
        } catch (UnsupportedEncodingException | PayPalRESTException | MalformedURLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    @Override
    public void finishSubscription(String token, String clientId, String secret, String planId) {
        Agreement agreement = new Agreement();
        agreement.setToken(token);

        try {
            Agreement activeAgreement = agreement.execute(new APIContext(clientId, secret, mode), agreement.getToken());
            activeAgreement.setState("active");
            AgreementWithPayee agreementWithPayee = new AgreementWithPayee();
            agreementWithPayee.setAgreement(activeAgreement);

            agreementWithPayee.setPayee(this.sellerRepository.
                    findSellerByClientIdAndSecret(clientId, secret).getJournalMail());
            storeTransactionToDatabase(null, agreementWithPayee);

            logger.info("Agreement created with ID {}", activeAgreement.getId());
        } catch (PayPalRESTException e) {
            logger.error(e.getDetails().getMessage());
        }
    }

    private void transferToPayee(Agreement agreement, String payee) {
        Payout payout = new Payout();
        PayoutSenderBatchHeader senderBatchHeader = new PayoutSenderBatchHeader();
        senderBatchHeader.setSenderBatchId(
                Double.toString(new Random().nextDouble()))
                .setEmailSubject(String.format("Subscription from '%s'," +
                                " for journal '%s'", agreement.getPayer().getPayerInfo().getEmail(),
                        agreement.getDescription())
                );
        Currency existingCurrency = agreement.getPlan().getPaymentDefinitions().get(0).getAmount();
        Currency amount = new Currency();
        amount.setValue(existingCurrency.getValue()).setCurrency("USD");
        PayoutItem senderItem = new PayoutItem();
        senderItem.setRecipientType("Email")
                .setNote(String.format("Subscription from '%s'," +
                                " for journal '%s'", agreement.getPayer().getPayerInfo().getEmail(),
                        agreement.getPlan().getName()))
                .setReceiver(payee)
                .setSenderItemId(String.valueOf(new Random().nextDouble())).setAmount(amount);
        List<PayoutItem> items = new ArrayList<>();
        items.add(senderItem);

        payout.setSenderBatchHeader(senderBatchHeader).setItems(items);

        PayoutBatch batch = null;
        try {
            // ###Create Payout Synchronous
            Map<String, String> parameters = new HashMap<>();
            parameters.put("sync_mode", "false");
            batch = payout.create(apiContext, parameters);
            logger.info("Payout Batch With ID: {}"
                    , batch.getBatchHeader().getPayoutBatchId());

        } catch (PayPalRESTException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void storeTransactionToDatabase(Payment payment, AgreementWithPayee agreementWithPayee) {
        TransactionPayment transactionPayment = null;
        if (agreementWithPayee == null) {
            Transaction transaction = payment.getTransactions().get(0);
            transactionPayment = TransactionPayment.builder()
                    .amount(String.valueOf(transaction.getAmount().getTotal()))
                    .currency(transaction.getAmount().getCurrency())
                    .createTime(payment.getCreateTime())
                    .updateTime(payment.getUpdateTime())
                    .failureReason(payment.getFailureReason())
                    .state(payment.getState())
                    .payerEmail(payment.getPayer().getPayerInfo().getEmail())
                    .payeeEmail(transaction.getPayee().getEmail())
                    .paymentId(payment.getId())
                    .paymentItem(transaction.getItemList().getItems().get(0).getName())
                    .typeOfPay(TypeOfPay.PAYMENT)
                    .build();
        } else {
            Agreement agreement = agreementWithPayee.getAgreement();
            transactionPayment = TransactionPayment.builder()
                    .amount(agreement.getPlan().getPaymentDefinitions().get(0).getAmount().getValue())
                    .currency("EUR")
                    .createTime(LocalDateTime.now().toString())
                    .state(agreement.getState())
                    .payerEmail(agreement.getPayer().getPayerInfo().getEmail())
                    .payeeEmail(agreementWithPayee.getPayee())
                    .paymentId(agreement.getId())
                    .paymentItem(agreement.getDescription())
                    .typeOfPay(TypeOfPay.SUBSCRIPTION)
                    .build();
        }

        this.transactionRepository.save(transactionPayment);
    }

    @Override
    public List<PlanInfo> getPlansByName(String name, String clientId, String secret) {
        List<JournalPlan> plans;
        List<PlanInfo> plansDto = new ArrayList<>();
        try {
            plans = this.journalPlanRepository.findJournalPlansByJournal(name);
        } catch (NullPointerException e) {
            return null;
        }
        PlanInfo planInfo = null;
        try {
            for (JournalPlan jp : plans) {
                Plan plan = Plan.get(new APIContext(clientId, secret, mode), jp.getPlanId());
                Currency amount = plan.getPaymentDefinitions().get(0).getAmount();
                String freq = plan.getPaymentDefinitions().get(0).getFrequency();
                String interval = plan.getPaymentDefinitions().get(0).getFrequencyInterval();
                planInfo = PlanInfo.builder().amount(amount.getValue())
                        .currency(amount.getCurrency())
                        .description(plan.getDescription())
                        .name(plan.getName())
                        .frequency(FrequencyPayment.valueOf(freq.toUpperCase()))
                        .frequencyInterval(interval)
                        .cycles(plan.getPaymentDefinitions().get(0).getCycles())
                        .planId(jp.getPlanId()).build();
                plansDto.add(planInfo);
            }
        } catch (PayPalRESTException e) {
            logger.error(e.getMessage());
        }
        return plansDto;
    }

    @Override
    public void addNewSeller(Seller seller) {
        if (this.sellerRepository.findSellerByJournalNameOrJournalMail(
                seller.getJournalName(), seller.getJournalMail()) == null) {
            this.sellerRepository.save(seller);
        }
    }

    @Override
    public String findJournalByIdAndSecret(String clientId, String clientSecret) {
        return this.sellerRepository.findSellerByClientIdAndSecret(clientId,
                clientSecret).getJournalName();
    }
}
