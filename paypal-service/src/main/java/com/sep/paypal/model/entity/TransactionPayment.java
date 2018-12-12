package com.sep.paypal.model.entity;

import com.sep.paypal.model.enumeration.TypeOfPay;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Builder
@Data
public class TransactionPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String amount;

    @Column
    private String paymentId;

    @Column
    private String payerEmail;

    @Column
    private String payeeEmail;

    @Column
    private String createTime;

    @Column
    private String updateTime;

    @Column
    private String state;

    @Column
    private String failureReason;

    @Column
    private String paymentItem;

    @Column
    private String currency;

    @Column
    @Enumerated
    private TypeOfPay typeOfPay;
}
