package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.ForumRepos;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ForumService {
    Forum createForum(Forum forum);
    List<Forum> findAllByPubliqueTrue();
    List<Forum> findAll();
    List<Forum> searchForumsByTitle(String title);
    List<Forum> findAllByUtilisateur(Utilisateur utilisateur);
    List<Forum> findAllByUtilisateurAndPublique(Utilisateur utilisateur);
    Forum findById(Long id);
    void deleteForum(Forum forum);
}
