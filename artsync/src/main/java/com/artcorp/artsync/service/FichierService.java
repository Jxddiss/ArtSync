package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;

import java.util.List;

public interface FichierService {

    FichierGeneral createFichier(FichierGeneral fichierGeneral);
    public List<FichierGeneral> findAllByProjetAndUtilisateur(Projet projet, Utilisateur utilisateur);
    Long countByProjet(Projet projet);
    void deleteAllByProjet(Projet projet);
}
