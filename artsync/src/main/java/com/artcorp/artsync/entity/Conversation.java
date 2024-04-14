package com.artcorp.artsync.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "conversation",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Chat> listeChat;
    @ManyToOne
    @JoinColumn(name = "utilisateur_un_id")
    private Utilisateur utilisateurUn;
    @ManyToOne
    @JoinColumn(name = "utilisateur_deux_id")
    private Utilisateur utilisateurDeux;
    @OneToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    public Conversation() {
    }

    public Conversation(Long id, Set<Chat> listeChat, Utilisateur utilisateurUn, Utilisateur utilisateurDeux, Projet projet) {
        this.id = id;
        this.listeChat = listeChat;
        this.utilisateurUn = utilisateurUn;
        this.utilisateurDeux = utilisateurDeux;
        this.projet = projet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Chat> getListeChat() {
        return listeChat;
    }

    public void setListeChat(Set<Chat> listeChat) {
        this.listeChat = listeChat;
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
}
