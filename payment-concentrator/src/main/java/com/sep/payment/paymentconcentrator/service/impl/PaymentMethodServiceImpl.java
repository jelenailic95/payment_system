package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Set<PaymentMethod> getPaymentMethods(Long clientId){
        Client pk = clientRepository.getOne(clientId);
        return pk.getPaymentMethods();
    }

    @Override
    public PaymentRequest createPaymentRequest(Long clientID, double amount){
        Client client = clientRepository.getOne(clientID);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(client.getMerchantId());
        paymentRequest.setMerchantPassword(client.getMerchantPassword());
        paymentRequest.setAmount(amount);
        paymentRequest.setMerchandTimestamp(new Date());
        return paymentRequest;
    }
}
