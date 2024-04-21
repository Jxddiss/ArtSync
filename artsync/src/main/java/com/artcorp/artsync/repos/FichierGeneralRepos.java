package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichierGeneralRepos extends JpaRepository<FichierGeneral, Long> {
    List<FichierGeneral> findAllByProjet(Projet projet);
    List<FichierGeneral> findAllByProjetAndUtilisateur(Projet projet, Utilisateur utilisateur);
    Long countByProjet(Projet projet);

}
