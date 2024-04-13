package com.artcorp.artsync.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateurs_amis")
public class UtilisateurAmis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
