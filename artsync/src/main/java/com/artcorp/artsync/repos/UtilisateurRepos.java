package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepos extends JpaRepository<Utilisateur, Long> {

    @NonNull
    @Query("select u from Utilisateur u where u.isActive = true")
    List<Utilisateur> findAll();
    @NonNull
    @Query("select u from Utilisateur u")
    List<Utilisateur> findAllAdmin();
    @Query("select u from Utilisateur u where u.email = ?1")
    Utilisateur findByEmail(String email);
    @Query("select u from Utilisateur u where u.pseudo = ?1")
    @EntityGraph(attributePaths = {"amis", "following", "followers"})
    Utilisateur findByPseudo(String pseudo);
    @Query("select u from Utilisateur u where u.specialisation = ?1 and u.isActive = true")
    List<Utilisateur> findBySpecialisationAndIsActive(String specialisation);
    @Query("select u from Utilisateur u where u.pseudo like %?1% or u.prenom like %?1% or u.nom like %?1% or u.specialisation like %?1% or u.statut like %?1% and u.isActive = true")
    List<Utilisateur> findByKeyword(String keyword);
    boolean existsByPseudoAndIdNot(String pseudo, Long id);
    boolean existsByEmailAndIdNot(String pseudo, Long id);
    @Modifying
    @Query("UPDATE Utilisateur u SET u.password = ?1 WHERE u.id = ?2")
    int changePassword(String password, Long id);
}
