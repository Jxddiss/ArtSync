package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Demande;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.DemandeRepos;
import com.artcorp.artsync.repos.ProjetRepos;
import com.artcorp.artsync.repos.UtilisateurRepos;
import com.artcorp.artsync.service.DemandeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DemandeServiceImpl implements DemandeService {
    DemandeRepos repos;
    ProjetRepos projetRepos;
    UtilisateurRepos utilisateurRepos;

    @Autowired
    public DemandeServiceImpl(DemandeRepos repos, ProjetRepos projetRepos, UtilisateurRepos utilisateurRepos) {
        this.repos = repos;
        this.projetRepos = projetRepos;
        this.utilisateurRepos = utilisateurRepos;
    }

    @Override
    public List<Demande> findAll() {
        return repos.findAll();
    }
    @Override
    public List<Demande> findByUtilisateurId(Long id) {
        return repos.findByUtilisateurId(id);
    }
    @Override
    public List<Demande> findByProjetId(Long id) {
        return repos.findByProjetId(id);
    }
    @Override
    public Demande createDemande(Long idUtilisateur, Long idProjet) {
        Demande demande = new Demande();
        Utilisateur utilisateur = utilisateurRepos.findById(idUtilisateur).get();
        demande.setUtilisateur(utilisateur);
        Projet projet = projetRepos.findById(idProjet).get();
        demande.setProjet(projet);
        demande.setStatus("pending");
        return repos.save(demande);
    }
    @Override
    public void deleteDemande(Long id) {
        repos.deleteById(id);
    }

    @Override
    public Optional<Demande> findById(Long demandeId) {
        return repos.findById(demandeId);
    }
    @Override
    public void deleteAllByProjetId(Long id) {
        repos.deleteAllByProjetId(id);
    }

}
