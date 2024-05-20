package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UtilisateurRepos extends JpaRepository<Utilisateur, Long> {

    @NonNull
    @Query("select u from Utilisateur u where u.isActive = true")
    List<Utilisateur> findAll();
    @Query("select u from Utilisateur u where u.isActive = true and u.email = ?1")
    Utilisateur findByEmailAndActive(String email);
    @Query("select u from Utilisateur u where u.pseudo = ?1 and u.isActive = true")
    @EntityGraph(attributePaths = {"amis", "following", "followers"})
    Utilisateur findByPseudoAndActive(String pseudo);
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
