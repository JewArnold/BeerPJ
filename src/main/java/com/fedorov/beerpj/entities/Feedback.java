package com.fedorov.beerpj.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
@Data
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "beer_name", referencedColumnName = "name")
    private Beer beerName;

    @Column(name = "text")
    private String text;

    @Column(name = "grade")
    private int grade;

}
