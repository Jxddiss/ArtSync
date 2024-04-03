package com.artcorp.artsync.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String etat;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    @ManyToOne
    @JoinColumn (name = "utilisateur_id")
    private Utilisateur utilisateur;

    public Tache() {
    }

    public Tache(Long id, String titre, String description, String etat, LocalDateTime dateCreation, LocalDateTime dateModification, Utilisateur utilisateur) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
