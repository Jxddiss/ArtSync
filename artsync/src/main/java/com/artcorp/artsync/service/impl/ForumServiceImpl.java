package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.repos.ForumRepos;
import com.artcorp.artsync.service.ForumService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
