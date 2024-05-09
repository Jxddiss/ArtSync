package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Chat;
import com.artcorp.artsync.repos.ChatRepos;
import com.artcorp.artsync.service.ChatService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {
    private ChatRepos chatRepos;

    @Autowired
    public ChatServiceImpl(ChatRepos chatRepos) {
        this.chatRepos = chatRepos;
    }

    @Override
    public List<Chat> findAllByConversationId(Long conversationId) {
        return chatRepos.findAllByConversationId(conversationId);
    }

    @Override
    public Chat save(Chat chat) {
        return chatRepos.save(chat);
    }

    @Override
    public void deleteAllByConversationId(Long id) {
        chatRepos.deleteAllByConversationId(id);
    }

    @Override
    public void deleteAllByUtilisateurUnId(Long id) {
        chatRepos.deleteAllByUtilisateurUnId(id);
    }
}
