package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.repos.ForumRepos;

import java.util.List;

public interface ForumService {
    Forum createForum(Forum forum);
    List<Forum> findAllByPubliqueTrue();
    List<Forum> findAll(); // Ajout de la méthode pour récupérer tous les forums

    List<Forum> findAllSubscribedForums(Long usserId);

    List<Forum> getAllForums();

    List<Forum> searchForumsByTitle(String title);
}
