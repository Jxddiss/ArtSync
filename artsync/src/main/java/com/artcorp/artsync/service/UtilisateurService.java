package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    Utilisateur inscription(String pseudo, String prenom, String nom, String email, String password, String photoUrl, String specialisation, String statut);
    List<Utilisateur> findAll();
    Utilisateur findByPseudo(String pseudo);
    Utilisateur findByEmail(String email);
    Utilisateur update(Utilisateur utilisateur);
    void updateFollowers(Long followedId, Long followerId);
}
