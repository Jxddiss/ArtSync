package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Demande;

import java.util.List;
import java.util.Optional;

public interface DemandeService {
    List<Demande> findAll();
    List<Demande> findByUtilisateurId(Long id);
    List<Demande> findByProjetId(Long id);
    Demande createDemande(Long idUtilisateur, Long idProjet);
    void deleteDemande(Long id);
    Optional<Demande> findById(Long demandeId);
}
