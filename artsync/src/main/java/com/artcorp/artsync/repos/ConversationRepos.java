package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepos extends JpaRepository<Conversation, Long> {
}
