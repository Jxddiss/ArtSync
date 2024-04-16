package com.artcorp.artsync.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    @ManyToOne
    @JoinColumn(name = "utilisateur_un_id", nullable = false)
    private Utilisateur utilisateurUn;
    @ManyToOne
    @JoinColumn(name = "utilisateur_deux_id")
    private Utilisateur utilisateurDeux;
    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;
    private LocalDateTime dateTimeEnvoie;
    private String message;
    private String urlMedia;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public Chat() {
    }

    public Chat(Long id, String type, Utilisateur utilisateurUn, Utilisateur utilisateurDeux, Projet projet, LocalDateTime dateTimeEnvoie, String message, String urlMedia) {
        this.id = id;
        this.type = type;
        this.utilisateurUn = utilisateurUn;
        this.utilisateurDeux = utilisateurDeux;
        this.projet = projet;
        this.dateTimeEnvoie = dateTimeEnvoie;
        this.message = message;
        this.urlMedia = urlMedia;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Utilisateur getUtilisateurUn() {
        return utilisateurUn;
    }

    public void setUtilisateurUn(Utilisateur utilisateurUn) {
        this.utilisateurUn = utilisateurUn;
    }

    public Utilisateur getUtilisateurDeux() {
        return utilisateurDeux;
    }

    public void setUtilisateurDeux(Utilisateur utilisateurDeux) {
        this.utilisateurDeux = utilisateurDeux;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public LocalDateTime getDateTimeEnvoie() {
        return dateTimeEnvoie;
    }

    public void setDateTimeEnvoie(LocalDateTime dateTimeEnvoie) {
        this.dateTimeEnvoie = dateTimeEnvoie;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", utilisateurUn=" + utilisateurUn +
                ", utilisateurDeux=" + utilisateurDeux +
                ", projet=" + projet +
                ", dateTimeEnvoie=" + dateTimeEnvoie +
                ", message='" + message + '\'' +
                ", urlMedia='" + urlMedia + '\'' +
                '}';
    }
}
