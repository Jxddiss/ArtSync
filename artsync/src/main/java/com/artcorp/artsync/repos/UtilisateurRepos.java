package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepos extends JpaRepository<Utilisateur, Long> {
    @Query("select u from Utilisateur u where u.isActive = true and u.email = ?1")
    Utilisateur findByEmailAndActive(String email);
    @Query("select u from Utilisateur u where u.pseudo = ?1 and u.isActive = true")
    Utilisateur findByPseudoAndActive(String pseudo);
    @Query("select u from Utilisateur u where u.specialisation = ?1 and u.isActive = true")
    List<Utilisateur> findBySpecialisationAndIsActive(String specialisation);
    @Query("select u from Utilisateur u where u.pseudo like %?1% or u.prenom like %?1% or u.nom like %?1% or u.specialisation like %?1% or u.statut like %?1% and u.isActive = true")
    List<Utilisateur> findByKeyword(String keyword);

}
