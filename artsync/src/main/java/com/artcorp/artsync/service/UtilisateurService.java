package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Notification;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.exception.domain.MauvaisIdentifiantException;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UtilisateurService {
    Utilisateur connexion(String username, String password) throws MauvaisIdentifiantException;
    Utilisateur inscription(String pseudo, String prenom, String nom, String email, String password, String photoUrl, String specialisation, String statut);
    List<Utilisateur> findAll();
    Utilisateur findByPseudo(String pseudo);
    Utilisateur findByEmail(String email);
    Utilisateur update(Utilisateur utilisateur);
    Notification updateRelations(Long followedId, Long followerId);
    List<Utilisateur> findBySpecialisation(String specialisation);
    List<Utilisateur> findByKeyword(String keyword);
    void disable(Long id);
    void enable(Long id);
    void delete(Long id);
    Utilisateur findById(Long idUtilisateur);

    String genLinkPasswordReset(String pseudo);
    boolean emailIsValid(String email, Long userId);
    boolean pseudoIsValid(String pseudo, Long userId);
    Utilisateur addUserSessionIfNot(HttpSession session, String username) throws NotConnectedException;
}
