package com.fedorov.beerpj.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "beer")
public class Beer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producer", referencedColumnName = "factory_name")
    private Producer producer;




    public Beer() {
    }


//    public enum Type {
//        LAGER, PALE_ALE, PORTER, STOUT, GOSE, WEISSBIER
//    }

}
