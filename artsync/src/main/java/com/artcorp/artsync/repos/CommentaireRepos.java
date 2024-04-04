package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaireRepos extends JpaRepository<Commentaire, Long> {
}
