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
    public Projet findById(Long id) {
        return repos.findById(id).get();
    }
    @Override
    public Projet addUtilisateurToProjet(Long idProjet, Long idUtilisateur) {
        Projet projet = repos.findById(idProjet).get();
        Utilisateur utilisateur = utilisateurRepos.findById(idUtilisateur).get();

        int presence = repos.checkIfUserIsInProjet(idProjet, idUtilisateur);
        if (presence > 0) {
            return projet;
        }
        projet.getUtilisateurs().add(utilisateur);
        return repos.save(projet);
    }
    @Override
    public Projet removeUtilisateurFromProjet(Long idProjet, Long idUtilisateur) {
        Projet projet = repos.findById(idProjet).get();
        Utilisateur utilisateur = utilisateurRepos.findById(idUtilisateur).get();
        projet.getUtilisateurs().remove(utilisateur);
        return repos.save(projet);
    }
    @Override
    public boolean checkIfUserIsInProjet(Long idProjet, Long idUtilisateur) {
        int presence = repos.checkIfUserIsInProjet(idProjet, idUtilisateur);
        return presence > 0;
    }
    @Override
    public List<Projet> findProjectsOfUser(Long idUtilisateur) {
        return repos.findProjectsOfUser(idUtilisateur);
    }
    @Override
    public int getMembersCount(Long idProjet) {
        return repos.getMembersCount(idProjet);
    }
    @Override
    public List<Utilisateur> getMembers(Long idProjet) {
        return repos.getMembers(idProjet);
    }
    @Override
    public int getFileCount(Long idProjet) {
        return repos.getFileCount(idProjet);
    }
    @Override
    public Projet updateProjet(Projet projet) {
        return repos.save(projet);
    }
    @Override
    public Projet createProjet(Projet projet) {
        return repos.save(projet);
    }

    @Override
    public void deleteProjet(Long id) {
        repos.deleteById(id);
    }

    @Override
    public List<Utilisateur> findByKeyWordAndProjet(Long projetId, String keyword) {
        return repos.findUsersOfProjectByKeyword(projetId,keyword);
    }

}
