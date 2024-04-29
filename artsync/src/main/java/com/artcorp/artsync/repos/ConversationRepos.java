package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Conversation;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepos extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c WHERE c.utilisateurUn = ?1 OR c.utilisateurDeux = ?1")
    List<Conversation> findByAllByUtilisateur(Utilisateur utilisateurUn);

    @Query("SELECT c FROM Conversation c WHERE c.projet = ?1")
    Conversation findByProjet(Projet projet);

    @Query("SELECT c FROM Conversation c WHERE c.utilisateurUn = ?1 AND c.utilisateurDeux = ?2 OR c.utilisateurUn = ?2 AND c.utilisateurDeux = ?1")
    Conversation findByUtilisateurDeuxAndUtilisateurUn(Utilisateur utilisateurUn, Utilisateur utilisateurDeux);
    void deleteAllByProjetId(Long id);
    void deleteById(Long id);
}
