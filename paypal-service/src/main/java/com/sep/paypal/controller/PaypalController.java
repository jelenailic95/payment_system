package com.sep.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.exception.NotAuthorizedException;
import com.sep.paypal.model.dto.*;
import com.sep.paypal.model.entity.Seller;
import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
import com.sep.paypal.security.AES;
import com.sep.paypal.service.PaypalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping
@Api("Controller for exposing Product Instance service via REST endpoint.")
public class PaypalController {

    private final PaypalService paypalService;

    @Autowired
    private AES aes;

    @Value("${paypal.mode}")
    private String mode;

    @Value("${client.host}")
    private String host;

    private Logger log = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;

    private static final String PAYPAL_SUCCESS_URL = "pay/success";

    @Autowired
    public PaypalController(PaypalService paypalService, RestTemplate restTemplate) {
        this.paypalService = paypalService;
        this.restTemplate = restTemplate;
    }

    @ApiOperation(value = "Pay for some journal ")
    @PostMapping(value = "pay")
    public String pay(@RequestBody PaymentDto request) throws PayPalRESTException {
        String cancelUrl;
        String successUrl;
        String clientId = aes.decrypt(request.getRequestDTO().getClientId());
        String clientSecret = aes.decrypt(request.getRequestDTO().getClientSecret());
        cancelUrl = host + "/result/cancel";
        successUrl = host + "/result/success"
                .concat("?id=").concat(request.getRequestDTO().getClientId())
                .concat("&secret=").concat(request.getRequestDTO().getClientSecret()
                .concat("&request=").concat(request.getPaymentRequest().getId().toString()));

//        String nameOfJournal = this.paypalService.findJournalByIdAndSecret(request.getClientId(), request.getClientSecret());
        Payment payment = paypalService.createPayment(
                request.getRequestDTO().getClientId(),
                request.getRequestDTO().getClientSecret(),
                request.getRequestDTO().getAmount(),
                "USD",
                PaymentMethod.PAYPAL,
                PaymentIntent.SALE,
                    "Order for ".concat(request.getPaymentRequest().getJournalName()),
                request.getPaymentRequest().getJournalName(),
                cancelUrl,
                successUrl);
        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                return links.getHref();
            }
        }
        return "redirect:/";
    }


    @ApiOperation(value = "If Pay succeed, finish payment")
    @GetMapping(value = PAYPAL_SUCCESS_URL)
    public boolean successPay(@RequestParam("id") String id, @RequestParam("secret") String secret,
                              @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId
                              ) {
        try {

            Payment payment = paypalService.executePayment(id, secret, paymentId, payerId);
            if (payment.getState().equals("approved")) {

                return true;
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @ApiOperation(value = "Add new seller to DB")
    @PostMapping(value = "user/add-seller")
    public ResponseEntity addNewSeller(@RequestBody SellerDto seller) {
        boolean exist = paypalService.isValidAccount(seller.getJournalMail());
        if (exist) {
            this.paypalService.addNewSeller(Seller.builder()
                    .journalMail(seller.getJournalMail())
                    .journalName(seller.getJournalName())
                    .clientId(seller.getClientId())
                    .secret(seller.getSecret()).build());
            return ResponseEntity.ok().build();
        }
        throw new NotAuthorizedException(seller.getJournalMail());
    }

    @ApiOperation(value = "Get some plan by name")
    @GetMapping(value = "get-plan")
    public ResponseEntity getPlanForJournal(@RequestParam("name") String name,
                                            @RequestParam("clientId") String clientId,
                                            @RequestParam("secret") String secret) {

        List<PlanInfo> plans = this.paypalService.getPlansByName(name, clientId, secret);
        if (plans != null || !plans.isEmpty()) {
            return ResponseEntity.ok(plans);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Create plan for some journal, on which users can subscribe")
    @PostMapping(value = "plan/create-plan")
    public ResponseEntity createPlanForSubscription(@RequestBody RequestCreatePlan requestCreatePlan) {
        paypalService.createPlanForSubscription(requestCreatePlan);
        return ResponseEntity.ok("Successfully created plan");
    }

    @ApiOperation(value = "Start with subscription on some journal")
    @PostMapping(value = "plan/subscribe")
    public ResponseEntity subscribeToPlan(@RequestBody SubscribeDto subscribeDto) {
        String clientId = subscribeDto.getClientId();
        String secret = subscribeDto.getSecret();
        URL url = paypalService.subscribeToPlan(subscribeDto.getNameOfJournal(),
                clientId, secret, subscribeDto.getPlanId());
        return ResponseEntity.ok(url);
    }

    @ApiOperation(value = "Finish subscription steps (Step before 'subscribeToPlan')")
    @GetMapping(value = "plan/finish-subscription")
    public ResponseEntity finishSubscription(@RequestParam("token") String token,
                                             @RequestParam("id") String clientId,
                                             @RequestParam("secret") String secret,
                                             @RequestParam("planId") String planId) {
        paypalService.finishSubscription(token, clientId, secret, planId);
        return ResponseEntity.ok("Subscription finished");
    }

}
