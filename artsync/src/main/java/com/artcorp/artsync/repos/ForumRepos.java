package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Conversation;
import com.artcorp.artsync.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
 public interface ForumRepos   extends JpaRepository<Forum, Long>{
}
