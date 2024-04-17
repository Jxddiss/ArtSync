package com.artcorp.artsync.entity;

import jakarta.persistence.*;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String code;
    @OneToOne
    @JoinColumn (name = "idUtilisateur")
    private Utilisateur utilisateur;
    public Portfolio() {
    }
    public Portfolio(Long id, String code, Utilisateur utilisateur) {
        this.id = id;
        this.code = code;
        this.utilisateur = utilisateur;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
