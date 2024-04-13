package com.artcorp.artsync.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateurs_relation")
public class UtilisateurRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
