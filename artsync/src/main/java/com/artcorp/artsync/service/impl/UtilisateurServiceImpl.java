package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.UtilisateurRepos;
import com.artcorp.artsync.service.UtilisateurService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurRepos repos;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepos repos) {
        this.repos = repos;
    }

    @Override
    public Utilisateur connexion(String username, String password) {
        return repos.findByPseudoAndPassword(username, password);
    }

    @Override
    public Utilisateur inscription(String pseudo, String prenom, String nom, String email, String password, String photoUrl, String specialisation, String statut) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(pseudo);
        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setEmail(email);
        utilisateur.setPassword(password);
        utilisateur.setPhotoUrl(photoUrl);
        utilisateur.setSpecialisation(specialisation);
        utilisateur.setStatut(statut);
        utilisateur.setActive(true);
        repos.save(utilisateur);
        return utilisateur;
    }

    @Override
    public List<Utilisateur> findAll() {
        return repos.findAll();
    }

    @Override
    public Utilisateur findByPseudo(String pseudo) {
        return repos.findByPseudoAndActive(pseudo);
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return repos.findByEmailAndActive(email);
    }

    @Override
    public Utilisateur findById(Long idUtilisateur) { return repos.findById(idUtilisateur).get(); }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        return repos.save(utilisateur);
    }

    @Override
    public void updateRelations(Long followedId, Long followerId) {
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
            repos.save(follower);
        }
    }

    @Override
    public List<Utilisateur> findBySpecialisation(String specialisation) {
        return repos.findBySpecialisationAndIsActive(specialisation);
    }

    @Override
    public List<Utilisateur> findByKeyword(String keyword) {
        return repos.findByKeyword(keyword);
    }

    @Override
    public void disable(Long id) {
        Utilisateur utilisateur = repos.findById(id).get();
        if (utilisateur != null) {
            utilisateur.setActive(false);
            repos.save(utilisateur);
        }
    }

    @Override
    public void enable(Long id) {
        Utilisateur utilisateur = repos.findById(id).get();
        if (utilisateur != null) {
            utilisateur.setActive(true);
            repos.save(utilisateur);
        }
    }

    @Override
    public void delete(Long id) {
        repos.deleteById(id);
    }

    @Override
    public int addFollower(Long followedId, Long followerId) {
        Utilisateur user1 = repos.findById(followedId).get();
        System.out.println(user1.getPseudo());
        Utilisateur user2 = repos.findById(followerId).get();
        System.out.println(user2.getPseudo());
        if (user1 != null && user2 != null) {
            user1.getFollowers().add(user2);
            user2.getFollowing().add(user1);
            updateRelations(followedId, followerId);
            repos.save(user1);
            repos.save(user2);
            return 1;
        }
        return 0;
    }
    @Override
    public int removeFollower(Long followedId, Long followerId) {
        Utilisateur user1 = repos.findById(followedId).get();
        Utilisateur user2 = repos.findById(followerId).get();
        if (user1 != null && user2 != null) {
            user1.getFollowers().remove(user2);
            user2.getFollowing().remove(user1);
            repos.save(user1);
            repos.save(user2);
            return 1;
        }
        return 0;
    }
}
