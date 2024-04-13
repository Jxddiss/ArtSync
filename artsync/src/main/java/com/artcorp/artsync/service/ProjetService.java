package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Projet;

import java.util.List;

public interface ProjetService {

    List<Projet> findAll();
    Projet findById(Long id);
    List<Projet> findByKeyword(String keyword);
    Projet addUtilisateurToProjet(Long idProjet, Long idUtilisateur);
    Projet removeUtilisateurFromProjet(Long idProjet, Long idUtilisateur);
    boolean checkIfUserIsInProjet(Long idProjet, Long idUtilisateur);
}
