package com.sep.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.model.RequestCreatePlan;
import com.sep.paypal.model.RequestPayment;
import com.sep.paypal.service.PaypalService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Api("Controller for exposing Product Instance service via REST endpoint.")
public class PaypalController {

    private final PaypalService paypalService;


    private Logger log = LoggerFactory.getLogger(getClass());

    public static final String PAYPAL_SUCCESS_URL = "pay/success";
    public static final String PAYPAL_CANCEL_URL = "pay/cancel";

    @Autowired
    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @PostMapping(value = "pay")
    public String pay(@RequestBody RequestPayment request) {
        String cancelUrl = null;
        String successUrl = null;
            cancelUrl = "http://localhost:8762/paypal/" + PAYPAL_CANCEL_URL;
            successUrl = "http://localhost:8762/paypal/" + PAYPAL_SUCCESS_URL;
        try {
            Payment payment = paypalService.createPayment(
                    request.getPrice(),
                    request.getCurrency(),
                    request.getPaymentMethod(),
                    request.getPaymentIntent(),
                    request.getDescription(),
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(value = PAYPAL_CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

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

    @GetMapping(value = "checkAccount")
    public ResponseEntity checkAccountStatus(@RequestParam String email) {
        boolean exist = paypalService.isValidAccount(email);
        if(!exist) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(email);
    }

    @PostMapping(value = "createPlan")
    public ResponseEntity createPlanForSubscription(@RequestBody RequestCreatePlan requestCreatePlan) {
        paypalService.createPlanForSubscription(requestCreatePlan);
        return ResponseEntity.ok().build();
    }

}
