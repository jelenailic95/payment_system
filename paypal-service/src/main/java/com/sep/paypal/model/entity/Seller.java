package com.sep.paypal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Seller {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String journalName;

    @Column
    private String journalMail;

    @Column
    private String clientId;


    @Column
    private String secret;
}
