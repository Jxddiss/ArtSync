package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Chat;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatService {
    @Query("SELECT c FROM Chat c WHERE c.conversation.id = ?1 ORDER BY c.dateTimeEnvoie DESC")
    List<Chat> findAllByConversationId(Long conversationId);
    Chat save(Chat chat);
    void deleteAllByConversationId(Long id);
    void deleteAllByUtilisateurUnId(Long id);
}
