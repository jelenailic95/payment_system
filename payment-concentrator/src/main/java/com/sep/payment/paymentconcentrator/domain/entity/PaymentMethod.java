package com.sep.payment.paymentconcentrator.domain.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PaymentMethod implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String methodName;   // bank1, Unicredit

    @Column
    private String method;  // bank,crypto


    public PaymentMethod() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
