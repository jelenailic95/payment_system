package com.sep.scientificcentre.scientificcentre.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String issnNumber;

    @Column
    private String name;

    @Column
    private boolean openAccess;

    @OneToMany
    private Set<Paper> papers;

    @Column
    private double price;

    @Column
    private int period;

}
