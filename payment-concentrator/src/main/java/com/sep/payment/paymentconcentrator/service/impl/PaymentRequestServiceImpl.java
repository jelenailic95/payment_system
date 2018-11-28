package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.repository.PaymentRequestRepository;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
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

    @Override
    public PaymentRequest createPaymentRequest(String client, double amount, String bankName) {
        Client foundClient = clientRepository.findByClientAndPaymentMethodName(client, bankName);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(foundClient.getClientId());
        paymentRequest.setMerchantPassword(foundClient.getClientPassword());

        Random random = new Random();
        paymentRequest.setMerchantOrderId(random.nextInt());
        paymentRequest.setAmount(amount);
        paymentRequest.setMerchandTimestamp(new Date());

        paymentRequestRepository.save(paymentRequest);

        return paymentRequest;
    }

    @Override
    public PaymentRequest getPaymentRequest(int merchantOrderId){
        return paymentRequestRepository.findByMerchantOrderId(merchantOrderId);
    }


}
