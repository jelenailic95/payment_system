package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.repository.PaymentRequestRepository;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Logger logger = LoggerFactory.getLogger(PaymentRequestServiceImpl.class);

    @Override
    public PaymentRequest createPaymentRequest(String client, double amount, String bankName) {
        Client foundClient = clientRepository.findByClientAndPaymentMethodName(client, bankName);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(foundClient.getClientId());
        paymentRequest.setMerchantPassword(foundClient.getClientPassword());

        Random random = new Random();
        paymentRequest.setMerchantOrderId(random.nextLong());
        paymentRequest.setAmount(amount);
        paymentRequest.setMerchandTimestamp(new Date());

        logger.info("Payment request is successfully created.");
        logger.info("Payment request - merchant order: {}", paymentRequest.getMerchantOrderId());

        paymentRequestRepository.save(paymentRequest);

        return paymentRequest;
    }

    @Override
    public PaymentRequest getPaymentRequest(Long merchantOrderId){
        return paymentRequestRepository.findByMerchantOrderId(merchantOrderId);
    }


}
