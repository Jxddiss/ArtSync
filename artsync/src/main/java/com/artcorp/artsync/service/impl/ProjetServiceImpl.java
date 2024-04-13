package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.ProjetRepos;
import com.artcorp.artsync.repos.UtilisateurRepos;
import com.artcorp.artsync.service.ProjetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProjetServiceImpl implements ProjetService {
    private ProjetRepos repos;
    private UtilisateurRepos utilisateurRepos;
    @Autowired
    public ProjetServiceImpl(ProjetRepos repos, UtilisateurRepos utilisateurRepos) {
        this.repos = repos;
        this.utilisateurRepos = utilisateurRepos;
    }
    @Override
    public List<Projet> findAll() {
        return repos.findAll();
    }
    @Override
    public List<Projet> findByKeyword(String keyword) {
        return repos.findByKeyword(keyword);
    }
    @Override
    public Projet addUtilisateurToProjet(Long idProjet, Long idUtilisateur) {
        Projet projet = repos.findById(idProjet).get();
        Utilisateur utilisateur = utilisateurRepos.findById(idUtilisateur).get();
        projet.getUtilisateurs().add(utilisateur);
        return repos.save(projet);
    }


}
