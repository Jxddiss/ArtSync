package com.artcorp.artsync.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = false, nullable = false, length = 64)
    private String titre;
    private LocalDateTime dateCreation;
    @Column(nullable = false, length = 125)
    private String filtres;
    private boolean publique;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "forum")
    private Set<Commentaire> listeCommentaires;
    public Forum() {
    }
    public Forum(Long id, String titre, LocalDateTime dateCreation, String filtres, boolean publique, Utilisateur utilisateur, Set<Commentaire> listeCommentaires) {
        this.id = id;
        this.titre = titre;
        this.dateCreation = dateCreation;
        this.filtres = filtres;
        this.publique = publique;
        this.utilisateur = utilisateur;
        this.listeCommentaires = listeCommentaires;
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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getFiltres() {
        return filtres;
    }

    public void setFiltres(String filtres) {
        this.filtres = filtres;
    }

    public boolean isPublique() {
        return publique;
    }

    public void setPublique(boolean publique) {
        this.publique = publique;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Set<Commentaire> getListeCommentaires() {
        return listeCommentaires;
    }

    public void setListeCommentaires(Set<Commentaire> listeCommentaires) {
        this.listeCommentaires = listeCommentaires;
    }
}
