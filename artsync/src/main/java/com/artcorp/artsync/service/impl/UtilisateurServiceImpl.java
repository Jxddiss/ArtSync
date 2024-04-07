package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.UtilisateurRepos;
import com.artcorp.artsync.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurRepos repos;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepos repos) {
        this.repos = repos;
    }

    @Override
    public Utilisateur inscription(String pseudo, String prenom, String nom, String email, String password, String photoUrl, String specialisation, String statut) {
        return null;
    }

    @Override
    public List<Utilisateur> findAll() {
        return null;
    }

    @Override
    public Utilisateur findByPseudo(String pseudo) {
        return null;
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return null;
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        return null;
    }

    @Override
    public void updateFollowers(Long followedId, Long followerId) {
        Utilisateur followed = repos.findById(followedId).get();
        Utilisateur follower = repos.findById(followerId).get();

        if (followed != null && follower != null) {
            if (followed.getFollowers().contains(follower)) {
                followed.getFollowers().remove(follower);
                follower.getFollowing().remove(followed);
                if (followed.getAmis().contains(follower)) {
                    followed.getAmis().remove(follower);
                    follower.getAmis().remove(followed);
                }
            } else {
                followed.getFollowers().add(follower);
                follower.getFollowing().add(followed);
                if (followed.getFollowing().contains(follower)) {
                    followed.getAmis().add(follower);
                    follower.getAmis().add(followed);
                }
            }
            repos.save(followed);
        }
    }

}
