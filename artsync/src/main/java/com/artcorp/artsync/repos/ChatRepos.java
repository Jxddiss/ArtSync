package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepos extends JpaRepository<Chat, Long> {
    List<Chat> findAllByConversationId(Long conversationId);

}
