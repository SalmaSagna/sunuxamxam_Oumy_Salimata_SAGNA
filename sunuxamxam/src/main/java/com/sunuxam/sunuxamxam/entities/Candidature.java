package com.sunuxam.sunuxamxam.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Utilisateur candidat;

    @ManyToOne
    @JoinColumn(name = "concours_id")
    private Concours concours;

    @Enumerated(EnumType.STRING)
    private StatutCandidature statut;

    private LocalDateTime dateCandidature;

    private String cheminCv;
    private String cheminPhoto;
    private String cheminDiplome;
}