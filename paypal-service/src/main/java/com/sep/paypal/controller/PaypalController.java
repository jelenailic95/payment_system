package com.sep.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.exception.NotAuthorizedException;
import com.sep.paypal.model.dto.*;
import com.sep.paypal.model.entity.Seller;
import com.sep.paypal.model.enumeration.PaymentIntent;
import com.sep.paypal.model.enumeration.PaymentMethod;
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

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping
@Api("Controller for exposing Product Instance service via REST endpoint.")
public class PaypalController {

    private final PaypalService paypalService;

    @Value("${paypal.mode}")
    private String mode;

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String PAYPAL_SUCCESS_URL = "pay/success";
    private static final String PAYPAL_CANCEL_URL = "pay/cancel";

    @Autowired
    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @ApiOperation(value = "Pay for some journal ")
    @PostMapping(value = "pay")
    public String pay(@RequestBody RequestPayment request) throws PayPalRESTException {
        String cancelUrl;
        String successUrl;
        cancelUrl = "http://localhost:8762/paypal-service/" + PAYPAL_CANCEL_URL;
        successUrl = "http://localhost:8762/paypal-service/".concat(PAYPAL_SUCCESS_URL)
                .concat("?id=").concat(request.getClientId()).concat("&secret=")
                .concat(request.getClientSecret());

        String nameOfJournal = this.paypalService.findJournalByIdAndSecret(request.getClientId(), request.getClientSecret());
        Payment payment = paypalService.createPayment(
                request.getClientId(),
                request.getClientSecret(),
                request.getAmount(),
                "USD",
                PaymentMethod.PAYPAL,
                PaymentIntent.SALE,
                "Order for ".concat(nameOfJournal),
                nameOfJournal,
                cancelUrl,
                successUrl);
        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                return links.getHref();
            }
        }
        return "redirect:/";
    }

    @ApiOperation(value = "If Pay canceled")
    @GetMapping(value = PAYPAL_CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }
    @CrossOrigin(origins = "https://localhost:4200")
    @ApiOperation(value = "If Pay succeed, finish payment")
    @GetMapping(value = PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("id") String id, @RequestParam("secret") String secret,
                             @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {

            Payment payment = paypalService.executePayment(id, secret, paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "redirect:/";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
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
    public ResponseEntity getPlanForJournal(@RequestParam("name") String name,@RequestHeader HttpHeaders headers) {
        String clientId = headers.get("clientId").get(0);
        String secret = headers.get("secret").get(0);

        PlanInfo planInfo = this.paypalService.getPlanByName(name, clientId, secret);
        if (planInfo != null) {
            return ResponseEntity.ok(planInfo);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Create plan for some journal, on which users can subscribe")
    @PostMapping(value = "plan/create-plan")
    public ResponseEntity createPlanForSubscription(@RequestBody RequestCreatePlan requestCreatePlan) {
        paypalService.createPlanForSubscription(requestCreatePlan);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Start with subscription on some journal")
    @PostMapping(value = "plan/subscribe")
    public ResponseEntity subscribeToPlan(@RequestBody SubscribeDto subscribeDto) {
        String clientId = subscribeDto.getClientId(); //headers.get("clientId").get(0);
        String secret = subscribeDto.getSecret(); //headers.get("secret").get(0);
        URL url = paypalService.subscribeToPlan(subscribeDto.getNameOfJournal(), clientId, secret);
        return ResponseEntity.ok(url);
    }

    @ApiOperation(value = "Finish subscription steps (Step before 'subscribeToPlan')")
    @GetMapping(value = "plan/finish-subscription")
    public ResponseEntity finishSubscription(@RequestParam("token") String token,
                                             @RequestParam("clientId") String clientId,
                                             @RequestParam("secret") String secret) {
        paypalService.finishSubscription(token, clientId, secret);
        return ResponseEntity.ok("Subscription finished");
    }

}
