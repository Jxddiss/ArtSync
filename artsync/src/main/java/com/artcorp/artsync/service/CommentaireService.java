package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Commentaire;
import com.artcorp.artsync.entity.Forum;

import java.util.List;

public interface CommentaireService {
    Commentaire save(Commentaire commentaire);
    List<Commentaire> findAllByForum(Forum forum);
}
