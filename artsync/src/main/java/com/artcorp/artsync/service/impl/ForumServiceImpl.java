package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.ForumRepos;
import com.artcorp.artsync.service.ForumService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ForumServiceImpl implements ForumService {
    @Autowired
    private ForumRepos forumRepos;

    @Override
    public Forum createForum(Forum forum) {
        if (forum==null){
            throw new IllegalArgumentException("Le forum ne peut pas être nul");
        }
        Forum forumCree = forumRepos.save(forum);
        return forumCree;
    }
    @Override
    public List<Forum> findAllByPubliqueTrue() {
        return forumRepos.findByPubliqueTrue();
    }
    @Override
    public List<Forum> findAll() {
        return forumRepos.findAll();
    }
    @Override
    public List<Forum> searchForumsByTitle(String title) {
        return forumRepos.findByKeyword(title);
    }
    @Override
    public List<Forum> findAllByUtilisateur(Utilisateur utilisateur) {
        return forumRepos.findAllByUtilisateur(utilisateur);
    }
    @Override
    public List<Forum> findAllByUtilisateurFollowing(Utilisateur utilisateur) {
        return forumRepos.findAllByUtilisateurFollowing(utilisateur);
    }
}
