package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Tache;

import java.util.List;

public interface TacheService {
    List<Tache> findAll();
    Tache findById(Long id);
    Tache findByTitre(String titre);
    Tache findByUtilisateurId(Long id);
    List<Tache> findByProjetId(Long id);
    List<Tache> findByUtilisateurIdAndProjetId(Long utilisateurId, Long projetId);
    List<Tache> findByEtatAndProjetId(String etat, Long projetId);
}
