package com.artcorp.artsync.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String pseudo;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String photoUrl;
    @Column(nullable = false)
    private String specialisation;
    private String statut;
    @OneToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @ManyToMany
    @JoinTable(name="utilisateurs_relation",
            joinColumns = @JoinColumn(name = "utilisateur_un_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_deux_id")
    )
    private List<Utilisateur> followers;

    @ManyToMany
    @JoinTable(name="utilisateurs_relation",
            joinColumns = @JoinColumn(name = "utilisateur_deux_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_un_id")
    )
    private List<Utilisateur> following;

    @ManyToMany
    @JoinTable(name="utilisateurs_amis",
            joinColumns = @JoinColumn(name = "utilisateur_un_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_deux_id")
    )
    private List<Utilisateur> amis;
    private boolean isActive;

    public Utilisateur() {
    }

    public Utilisateur(Long id, String pseudo, String prenom, String email, String password, String photoUrl, String specialisation, String statut, Portfolio portfolio, List<Utilisateur> followers, List<Utilisateur> following, List<Utilisateur> amis, List<Projet> projetFavories) {
        this.id = id;
        this.pseudo = pseudo;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.photoUrl = photoUrl;
        this.specialisation = specialisation;
        this.statut = statut;
        this.portfolio = portfolio;
        this.followers = followers;
        this.following = following;
        this.amis = amis;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public List<Utilisateur> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Utilisateur> followers) {
        this.followers = followers;
    }

    public List<Utilisateur> getFollowing() {
        return following;
    }

    public void setFollowing(List<Utilisateur> following) {
        this.following = following;
    }

    public List<Utilisateur> getAmis() {
        return amis;
    }

    public void setAmis(List<Utilisateur> amis) {
        this.amis = amis;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", specialisation='" + specialisation + '\'' +
                ", statut='" + statut + '\'' +
                ", portfolio=" + portfolio +
                ", followers=" + followers +
                ", following=" + following +
                ", amis=" + amis +
                '}';
    }
}
