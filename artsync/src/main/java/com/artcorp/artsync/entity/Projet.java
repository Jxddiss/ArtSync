package com.artcorp.artsync.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique =true , nullable = false)
    private String titre;
    @Column(nullable = false)
    private String description;
    private String projetPhoto;
    private boolean publique;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private LocalDateTime dateSuppression;
    @OneToOne
    @JoinColumn (name = "conversation_id")
    private Conversation conversation;
    @OneToMany
    @JoinColumn (name = "utilisateur_id")
    private List<Utilisateur> utilisateurs;
    @OneToMany
    @JoinColumn (name = "fichier_id")
    private List<FichierGeneral> fichiers;
    private int nbMembre;

    public Projet() {
    }
    public Projet(Long id, String titre, String description, String projetPhoto, boolean publique, LocalDateTime dateCreation, LocalDateTime dateModification, LocalDateTime dateSuppression, Conversation conversation, List<Utilisateur> utilisateurs, List<FichierGeneral> fichiers) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.projetPhoto = projetPhoto;
        this.publique = publique;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.dateSuppression = dateSuppression;
        this.conversation = conversation;
        this.utilisateurs = utilisateurs;
        this.fichiers = fichiers;
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

    public String getProjetPhoto() {
        return projetPhoto;
    }

    public void setProjetPhoto(String projetPhoto) {
        this.projetPhoto = projetPhoto;
    }

    public boolean isPublique() {
        return publique;
    }

    public void setPublique(boolean publique) {
        this.publique = publique;
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

    public LocalDateTime getDateSuppression() {
        return dateSuppression;
    }

    public void setDateSuppression(LocalDateTime dateSuppression) {
        this.dateSuppression = dateSuppression;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public List<FichierGeneral> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<FichierGeneral> fichiers) {
        this.fichiers = fichiers;
    }

    public int getNbMembre() {
        return nbMembre;
    }
    public void setNbMembre(int nbMembre) {
        this.nbMembre = nbMembre;
    }
}
