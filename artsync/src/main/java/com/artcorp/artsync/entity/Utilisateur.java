package com.artcorp.artsync.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 64)
    private String pseudo;
    @Column(nullable = false, length = 64)
    private String prenom;
    @Column(nullable = false, length = 64)
    private String nom;
    @Column(nullable = false, length = 64,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String photoUrl;
    @Column(nullable = false, length = 64)
    private String specialisation;
    @Column(nullable = false, length = 64)
    private String statut;
    private String backgroundColor;
    private String backgroundTexture;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="utilisateurs_relation",
            joinColumns = @JoinColumn(name = "utilisateur_un_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_deux_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id"})
    )
    private Set<Utilisateur> followers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="utilisateurs_relation",
            joinColumns = @JoinColumn(name = "utilisateur_deux_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_un_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id"})
    )
    private Set<Utilisateur> following;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="utilisateurs_amis",
            joinColumns = @JoinColumn(name = "utilisateur_un_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_deux_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id"})
    )
    private Set<Utilisateur> amis;
    private boolean isActive;
    @Transient
    private boolean in = false;

    public Utilisateur() {
    }

    public Utilisateur(Long id, String pseudo, String prenom, String email, String password, String photoUrl, String specialisation, String statut, Portfolio portfolio, Set<Utilisateur> followers, Set<Utilisateur> following, Set<Utilisateur> amis, List<Projet> projetFavories, String backgroundColor, String backgroundTexture) {
        this.id = id;
        this.pseudo = pseudo;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.photoUrl = photoUrl;
        this.specialisation = specialisation;
        this.statut = statut;
        this.followers = followers;
        this.following = following;
        this.amis = amis;
        this.backgroundColor = backgroundColor;
        this.backgroundTexture = backgroundTexture;
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

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(String backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public Set<Utilisateur> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Utilisateur> followers) {
        this.followers = followers;
    }

    public Set<Utilisateur> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Utilisateur> following) {
        this.following = following;
    }

    public Set<Utilisateur> getAmis() {
        return amis;
    }

    public void setAmis(Set<Utilisateur> amis) {
        this.amis = amis;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
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

                '}';
    }
}
