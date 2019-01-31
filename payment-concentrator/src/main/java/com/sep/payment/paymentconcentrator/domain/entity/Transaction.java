package com.sep.payment.paymentconcentrator.domain.entity;


import com.sep.payment.paymentconcentrator.domain.TransactionStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long merchantOrderId;

    @Column
    private Long acquirerOrderId;

    @Column
    private Date acquirerTimestamp;

    @Column
    private String paymentId;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column
    private double amount;

    public Transaction() {
    }

    public Long getId() {

        return id;
    }

    public Transaction(Long merchantOrderId, Long acquirerOrderId, Date acquirerTimestamp, String paymentId,
                       TransactionStatus status, double amount) {
        this.merchantOrderId = merchantOrderId;
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.paymentId = paymentId;
        this.status = status;
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Long getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(Long acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
