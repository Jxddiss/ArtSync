package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.FichierGeneralRepos;
import com.artcorp.artsync.service.FichierService;
import jakarta.transaction.Transactional;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FichierServiceImpl implements FichierService {
    private final FichierGeneralRepos fichierGeneralRepository;

    @Autowired
    public FichierServiceImpl(FichierGeneralRepos fichierGeneralRepository) {
        this.fichierGeneralRepository = fichierGeneralRepository;
    }


    @Override
    public FichierGeneral createFichier(FichierGeneral fichierGeneral) {
        fichierGeneralRepository.save(fichierGeneral);
        return fichierGeneral;
    }

    @Override
    public List<FichierGeneral> findAllByProjetAndUtilisateur(Projet projet, Utilisateur utilisateur) {
        return fichierGeneralRepository.findAllByProjetAndUtilisateur(projet, utilisateur);
    }
    @Override
    public Long countByProjet(Projet projet) {
        return fichierGeneralRepository.countByProjet(projet);
    }
}
