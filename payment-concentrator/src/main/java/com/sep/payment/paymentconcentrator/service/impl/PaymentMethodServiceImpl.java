package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.repository.PaymentRequestRepository;
import com.sep.payment.paymentconcentrator.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.Set;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;


    @Override
    public Set<PaymentMethod> getPaymentMethods(Long clientId){
        Client client = clientRepository.getOne(clientId);
        return client.getPaymentMethods();
    }

    @Override
    public PaymentRequest createPaymentRequest(Long clientID, double amount){
        Client client = clientRepository.getOne(clientID);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(client.getMerchantId());
        paymentRequest.setMerchantPassword(client.getMerchantPassword());

        Random random = new Random();
        paymentRequest.setMerchantOrderId(random.nextDouble());
        paymentRequest.setAmount(amount);
        paymentRequest.setMerchandTimestamp(new Date());

        paymentRequestRepository.save(paymentRequest);

        return paymentRequest;
    }
}
