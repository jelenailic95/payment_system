package com.sep.paypal.controller;

import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.sep.paypal.PaypalClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping
@Api("Controller for exposing Product Instance service via REST endpoint.")

public class PaypalController {

    @Autowired
    private PaypalClient paypalClient;

    @PostMapping(value = "/make/payment")
    @ApiOperation("Returns a list of MSISDN numbers for the given account id.")
    public Map<String, Object> makePayment(@RequestParam("sum") String sum,
                                           @RequestParam("clientId") String clientId,
                                           @RequestParam("clientSecret") String clientSecret){
         return paypalClient.createPayment(sum, clientId, clientSecret);

        //return "";
    }


    @PostMapping(value = "/completePayment")
    public Map<String, Object> makePayment(HttpServletRequest request, String clientId, String clientSecret) {
        return paypalClient.completePayment(request, clientId, clientSecret);
    }

}
