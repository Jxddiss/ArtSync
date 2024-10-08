package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Commentaire;
import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.CommentaireRepos;
import com.artcorp.artsync.service.CommentaireService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CommentaireServiceImpl implements CommentaireService {
    private CommentaireRepos commentaireRepos;
    @Autowired
    public CommentaireServiceImpl(CommentaireRepos commentaireRepos) {
        this.commentaireRepos = commentaireRepos;
    }

    @Override
    public Commentaire save(Commentaire commentaire) {
        return commentaireRepos.save(commentaire);
    }

    @Override
    public List<Commentaire> findAllByForum(Forum forum) {
        return commentaireRepos.findAllByForum(forum);
    }

    @Override
    public void deleteCommentaire(Commentaire commentaire) {
        commentaireRepos.delete(commentaire);
    }

    @Override
    public List<Commentaire> findAllByUser(Utilisateur utilisateur) {
        return commentaireRepos.findAllByUtilisateur(utilisateur);
    }

    @Override
    public Commentaire findById(Long id) {
        return commentaireRepos.findCommentaireById(id);
    }
}
