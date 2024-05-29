package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichierGeneralRepos extends JpaRepository<FichierGeneral, Long> {
    List<FichierGeneral> findAllByProjet(Projet projet);
    @Query("select f from FichierGeneral f where f.utilisateur = ?1 AND f.projet IS NOT NULL ")
    List<FichierGeneral> findAllByUtilisateur(Utilisateur utilisateur);
    List<FichierGeneral> findAllByProjetAndUtilisateur(Projet projet, Utilisateur utilisateur);
    Long countByProjet(Projet projet);
    void deleteAllByProjet(Projet projet);

}
