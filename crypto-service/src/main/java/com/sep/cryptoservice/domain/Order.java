package com.sep.cryptoservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String order_id;
    @Column
    private double price_amount;
    @Column
    private String price_currency;
    @Column
    private String receive_currency;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String callback_url;
    @Column
    private String cancel_url;
    @Column
    private String success_url;
    @Column
    private String token;

    public Order() {
 }

    public Order(double price_amount, String price_currency, String receive_currency, String success_url, String cancel_url) {
        this.price_amount = price_amount;
        this.price_currency = price_currency;
       this.receive_currency = receive_currency;
        this.success_url = success_url;
        this.cancel_url = cancel_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public double getPrice_amount() {
        return price_amount;
    }

    public void setPrice_amount(double price_amount) {
        this.price_amount = price_amount;
    }

    public String getPrice_currency() {
        return price_currency;
    }

    public void setPrice_currency(String price_currency) {
        this.price_currency = price_currency;
    }

    public String getReceive_currency() {
        return receive_currency;
    }

    public void setReceive_currency(String receive_currency) {
        this.receive_currency = receive_currency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public String getCancel_url() {
        return cancel_url;
    }

    public void setCancel_url(String cancel_url) {
        this.cancel_url = cancel_url;
    }

    public String getSuccess_url() {
        return success_url;
    }

    public void setSuccess_url(String success_url) {
        this.success_url = success_url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
