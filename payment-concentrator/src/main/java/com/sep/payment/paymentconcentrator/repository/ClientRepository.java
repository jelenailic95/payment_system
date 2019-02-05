package com.sep.payment.paymentconcentrator.repository;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByClient(String client);
    Client findByClientAndPaymentMethodMethodName(String client, String paymentMethod);
    Client findByClientAndPaymentMethodMethodAndPaymentMethodMethodName(String client, String method, String methodName);
}
