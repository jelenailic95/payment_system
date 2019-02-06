package com.sep.scientificcentre.scientificcentre.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String city;

    @Column
    private int zipCode;

    @Column
    private String country;

    @Column
    private String role;

    @OneToMany
    private List<PaidJournal> journals;

    @OneToMany
    private List<Paper> papers;

    @OneToMany
    private Set<Journal> myJournals;


}
