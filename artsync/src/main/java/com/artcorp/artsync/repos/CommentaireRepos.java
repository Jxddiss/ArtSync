package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Commentaire;
import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepos extends JpaRepository<Commentaire,Long> {
    List<Commentaire> findAllByForum(Forum forum);
    List<Commentaire> findAllByUtilisateur(Utilisateur utilisateur);
    Commentaire findCommentaireById(Long id);
}
