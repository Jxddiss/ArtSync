package com.artcorp.artsync.entity;

import jakarta.persistence.*;

@Entity
public class LiveStream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String pseudoStreamer;
    private boolean active;
    @Transient
    private Utilisateur utilisateur;


    public LiveStream() {
    }

    public LiveStream(Long id, String titre,String pseudoStreamer, boolean active) {
        this.id = id;
        this.titre = titre;
        this.pseudoStreamer = pseudoStreamer;
        this.active = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPseudoStreamer() {
        return pseudoStreamer;
    }

    public void setPseudoStreamer(String pseudoStreamer) {
        this.pseudoStreamer = pseudoStreamer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
