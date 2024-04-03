package com.artcorp.artsync.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "conversation")
    private Set<Chat> listeChat;
    @OneToOne
    @JoinColumn(name = "utilisateur_un_id")
    private Utilisateur utilisateurUn;
    @OneToOne
    @JoinColumn(name = "utilisateur_deux_id")
    private Utilisateur utilisateurDeux;
    @OneToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
