package com.sep.scientificcentre.scientificcentre.entity;

//import org.elasticsearch.common.geo.GeoPoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

//import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String keyWords;

    @Column
    private String abstrect;

    @Column(length = 1000000)
    private String context;

    @Column
    private Boolean accepted;

    @ManyToOne
    private User mainAuthor;


    @ManyToMany
    private List<User> authors;

    @Column
    private String doi;

    @Column
    private double price;


//    @ManyToOne
//    private Journal journal;

}