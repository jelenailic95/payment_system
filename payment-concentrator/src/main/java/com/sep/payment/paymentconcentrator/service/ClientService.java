package com.sep.payment.paymentconcentrator.service;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    List<Client> getAllMethods(String client);
    Client methodSubscribe(String clientName, String clientId, String clientPassword, String method, String methodName);
    void methodUnsubscribe(String client, String method, String methodName);
    Client findByClientMethod(String client, String method);
    List<PaymentMethod> findPaymentMethodByMethod (String method);
}
