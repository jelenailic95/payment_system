//package com.sep.authservice.entity;
//
//
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//public class Client implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
////    @Column
//    private String client;    // seller's token(id) from scientific centre
//
//    @Column
//    private String journal;
//
//    @Column
//    private String clientId;
//
//    @Column
//    private String clientPassword;
//
//    @ManyToOne
//    private PaymentMethod paymentMethod;
//
//    public Client() {
//    }
//
//    public Client(String client, String journal, String clientId, String clientPassword, PaymentMethod paymentMethod) {
//        this.client = client;
//        this.journal = journal;
//        this.clientId = clientId;
//        this.clientPassword = clientPassword;
//        this.paymentMethod = paymentMethod;
//    }
//
//    public String getClientId() {
//        return clientId;
//    }
//
//    public void setClientId(String clientId) {
//        this.clientId = clientId;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getJournal() {
//        return journal;
//    }
//
//    public void setJournal(String journal) {
//        this.journal = journal;
//    }
//
//    public String getClient() {
//        return client;
//    }
//
//    public void setClient(String client) {
//        this.client = client;
//    }
//
//    public String getClientPassword() {
//        return clientPassword;
//    }
//
//    public void setClientPassword(String clientPassword) {
//        this.clientPassword = clientPassword;
//    }
//
//    public PaymentMethod getPaymentMethod() {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(PaymentMethod paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }
//}
