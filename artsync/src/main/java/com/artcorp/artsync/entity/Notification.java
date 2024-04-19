package com.artcorp.artsync.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudoSender;
    private String message;
    private String titre;
    private boolean appel;
    private String type;
    private String urlNotif;

    public Notification() {
    }

    public Notification(Long id, String pseudoSender, String message, String titre, boolean appel, String type, String urlNotif) {
        this.id = id;
        this.pseudoSender = pseudoSender;
        this.message = message;
        this.titre = titre;
        this.appel = appel;
        this.type = type;
        this.urlNotif = urlNotif;
    }

    public String getPseudoSender() {
        return pseudoSender;
    }

    public void setPseudoSender(String pseudoSender) {
        this.pseudoSender = pseudoSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isAppel() {
        return appel;
    }

    public void setAppel(boolean appel) {
        this.appel = appel;
    }

    public String getUrlNotif() {
        return urlNotif;
    }

    public void setUrlNotif(String urlNotif) {
        this.urlNotif = urlNotif;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
