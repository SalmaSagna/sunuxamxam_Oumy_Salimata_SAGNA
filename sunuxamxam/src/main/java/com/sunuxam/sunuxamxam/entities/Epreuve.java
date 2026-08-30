package com.sunuxam.sunuxamxam.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Epreuve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private Double coefficient;
    private Integer duree; // en minutes

    @ManyToOne
    @JoinColumn(name = "concours_id")
    private Concours concours;
}