package com.sep.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.exception.NotAuthorizedException;
import com.sep.paypal.model.dto.*;
import com.sep.paypal.model.entity.Seller;
import com.sep.paypal.service.PaypalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URL;

@RestController
@RequestMapping
@Api("Controller for exposing Product Instance service via REST endpoint.")
public class PaypalController {

    private final PaypalService paypalService;


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
        successUrl = "http://localhost:8762/paypal-service/" + PAYPAL_SUCCESS_URL;
        Payment payment = paypalService.createPayment(
                request.getPrice(),
                request.getCurrency(),
                request.getPaymentMethod(),
                request.getPaymentIntent(),
                request.getDescription(),
                request.getEmailOfPayee(),
                request.getNameOfJournal(),
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

    @ApiOperation(value = "If Pay succeed, finish payment")
    @GetMapping(value = PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "Success";
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
                    .journalName(seller.getJournalName()).build());
            return ResponseEntity.ok().build();
        }
        throw new NotAuthorizedException(seller.getJournalMail());
    }

    @ApiOperation(value = "Get some plan by name")
    @GetMapping(value = "get-plan")
    public ResponseEntity getPlanForJournal(@RequestParam("name") String name) {
        PlanInfo planInfo = this.paypalService.getPlanByName(name);
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
        URL url = paypalService.subscribeToPlan(subscribeDto.getNameOfJournal());
        return ResponseEntity.ok(url);
    }

    @ApiOperation(value = "Finish subscription steps (Step before 'subscribeToPlan')")
    @GetMapping(value = "plan/finish-subscription")
    public ResponseEntity finishSubscription(@RequestParam("token") String token) {
        paypalService.finishSubscription(token);
        return ResponseEntity.ok("Subscription finished");
    }

}
