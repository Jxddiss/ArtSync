package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Tache;
import com.artcorp.artsync.repos.TacheRepos;
import com.artcorp.artsync.repos.UtilisateurRepos;
import com.artcorp.artsync.service.TacheService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TacheServiceImpl implements TacheService {
    private TacheRepos repos;
    private UtilisateurRepos utilisateurRepos;

    @Autowired
    public TacheServiceImpl(TacheRepos repos, UtilisateurRepos utilisateurRepos) {
        this.repos = repos;
        this.utilisateurRepos = utilisateurRepos;
    }


    @Override
    public List<Tache> findAll() {
        return repos.findAll();
    }

    @Override
    public Tache findById(Long id) {
        return repos.findById(id).get();
    }

    @Override
    public Tache findByTitre(String titre) {
        return repos.findByTitre(titre);
    }

    @Override
    public Tache findByUtilisateurId(Long id) {
        return repos.findByUtilisateurId(id);
    }

    @Override
    public List<Tache> findByProjetId(Long id) {
        return repos.findByProjetId(id);
    }

    @Override
    public List<Tache> findByUtilisateurIdAndProjetId(Long utilisateurId, Long projetId) {
        return repos.findByUtilisateurIdAndProjetId(utilisateurId, projetId);
    }

    @Override
    public List<Tache> findByEtatAndProjetId(String etat, Long projetId) {
        return repos.findByEtatAndProjetId(etat, projetId);
    }
    @Override
    public Tache createTache(Tache tache) {
        return repos.save(tache);
    }
    @Override
    public void deleteTache(Long id) {
        repos.deleteById(id);
    }
    @Override
    public Tache updateTache(Tache tache) {
        return repos.save(tache);
    }
    @Override
    public void deleteAllByProjetId(Long id) {
        repos.deleteAllByProjetId(id);
    }
}
