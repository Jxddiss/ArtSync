package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Demande;

import java.util.List;

public interface DemandeService {
    List<Demande> findAll();
    List<Demande> findByUtilisateurId(Long id);
    List<Demande> findByProjetId(Long id);
    Demande createDemande(Long idUtilisateur, Long idProjet);

}
