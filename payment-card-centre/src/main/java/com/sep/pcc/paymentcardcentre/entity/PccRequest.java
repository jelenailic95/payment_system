package com.sep.pcc.paymentcardcentre.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
public class PccRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String issuerBank;

    @Column
    private Date acquirerTimestamp;

    @Column
    private Long acquirerOrderId;

    @Column
    private Double amount;

    public PccRequest(Date acquirerTimestamp, Long acquirerOrderId, Double amount) {
        this.acquirerTimestamp = acquirerTimestamp;
        this.acquirerOrderId = acquirerOrderId;
        this.amount = amount;
    }

    public PccRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssuerBank() {
        return issuerBank;
    }

    public void setIssuerBank(String issuerBank) {
        this.issuerBank = issuerBank;
    }

    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public Long getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(Long acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
