package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Forum;

import java.util.List;

public interface ForumService {
    Forum createForum(Forum forum);
    List<Forum> findAllByPubliqueTrue();
}
