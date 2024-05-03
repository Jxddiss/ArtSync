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
    private String imgSender;
    private String message;
    private String titre;
    private boolean appel;
    private String type;
    private String urlNotif;
    private boolean lu;
    private Long idDest;

    public Notification() {
    }

    public Notification(Long id, String pseudoSender, String imgSender, String message, String titre, boolean appel, String type, String urlNotif, boolean lu, Long idDest) {
        this.id = id;
        this.pseudoSender = pseudoSender;
        this.imgSender = imgSender;
        this.message = message;
        this.titre = titre;
        this.appel = appel;
        this.type = type;
        this.urlNotif = urlNotif;
        this.lu = lu;
        this.idDest = idDest;
    }

    public String getPseudoSender() {
        return pseudoSender;
    }

    public void setPseudoSender(String pseudoSender) {
        this.pseudoSender = pseudoSender;
    }

    public String getImgSender() {
        return imgSender;
    }

    public void setImgSender(String imgSender) {
        this.imgSender = imgSender;
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

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public Long getIdDest() {
        return idDest;
    }

    public void setIdDest(Long idDest) {
        this.idDest = idDest;
    }
}
