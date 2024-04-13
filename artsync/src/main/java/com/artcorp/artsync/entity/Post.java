package com.artcorp.artsync.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String titre;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String texte;
    private boolean publique;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FichierGeneral> listeFichiers;
    private int nbLikes;
    private int nbPartages;
    private int nbVues;
    private String type;
    private String pseudoUtilisateur;
    private String profilUrl;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "post")
    private Set<Commentaire> listeCommentaires;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public boolean isPublique() {
        return publique;
    }

    public void setPublique(boolean publique) {
        this.publique = publique;
    }

    public Set<FichierGeneral> getListeFichiers() {
        return listeFichiers;
    }

    public void setListeFichiers(Set<FichierGeneral> listeFichiers) {
        this.listeFichiers = listeFichiers;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    public int getNbPartages() {
        return nbPartages;
    }

    public void setNbPartages(int nbPartages) {
        this.nbPartages = nbPartages;
    }

    public int getNbVues() {
        return nbVues;
    }

    public void setNbVues(int nbVues) {
        this.nbVues = nbVues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPseudoUtilisateur() {
        return pseudoUtilisateur;
    }

    public void setPseudoUtilisateur(String pseudoUtilisateur) {
        this.pseudoUtilisateur = pseudoUtilisateur;
    }

    public String getProfilUrl() {
        return profilUrl;
    }

    public void setProfilUrl(String profilUrl) {
        this.profilUrl = profilUrl;
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

    public void addCommentaire(Commentaire commentaire) {
        this.listeCommentaires.add(commentaire);
    }

    public void addFichier(FichierGeneral fichier) {
        this.listeFichiers.add(fichier);
    }
}
