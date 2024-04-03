package com.artcorp.artsync.entity;

import jakarta.persistence.*;

@Entity
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String description;
    private String etat;
    private String dateCreation;
    private String dateModification;
    @ManyToOne
    private Utilisateur utilisateur;

    public Tache() {
    }

    public Tache(int id, String titre, String description, String etat, String dateCreation, String dateModification, Utilisateur utilisateur) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateModification() {
        return dateModification;
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
