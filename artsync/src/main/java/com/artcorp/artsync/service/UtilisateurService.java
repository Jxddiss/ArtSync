package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    Utilisateur connexion(String username, String password);
    Utilisateur inscription(String pseudo, String prenom, String nom, String email, String password, String photoUrl, String specialisation, String statut);
    List<Utilisateur> findAll();
    Utilisateur findByPseudo(String pseudo);
    Utilisateur findByEmail(String email);
    Utilisateur update(Utilisateur utilisateur);
    void updateRelations(Long followedId, Long followerId);
    List<Utilisateur> findBySpecialisation(String specialisation);
    List<Utilisateur> findByKeyword(String keyword);
    void disable(Long id);
    void enable(Long id);
    void delete(Long id);
}
