package com.sep.payment.paymentconcentrator.domain.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class PaymentClient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long clientId;    // seller's token(id) from scientific centre

    @ManyToMany
    private Set<PaymentMethod> paymentMethods;

    public PaymentClient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
