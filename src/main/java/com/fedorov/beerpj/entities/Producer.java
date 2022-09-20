package com.fedorov.beerpj.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "producer")
public class Producer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "factory_name")
    private String factoryName;


    @OneToMany(mappedBy = "producer")
    private List<Beer> beerList;



}