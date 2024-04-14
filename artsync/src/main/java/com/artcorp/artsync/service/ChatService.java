package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Chat;

import java.util.List;

public interface ChatService {
    List<Chat> findAllByConversationId(Long conversationId);
    Chat save(Chat chat);
}
