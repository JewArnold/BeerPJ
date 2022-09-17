package com.fedorov.beerpj.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producer", referencedColumnName = "factory_name")
    private Producer producer;




    public Beer() {
    }


//    public enum Type {
//        LAGER, PALE_ALE, PORTER, STOUT, GOSE, WEISSBIER
//    }

}
