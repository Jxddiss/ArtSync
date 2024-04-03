package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepos extends JpaRepository<Utilisateur, Long> {

}
