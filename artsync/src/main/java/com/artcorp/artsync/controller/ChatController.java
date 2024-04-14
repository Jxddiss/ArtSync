package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Chat;
import com.artcorp.artsync.service.ChatService;
import com.artcorp.artsync.service.impl.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController {
    private ChatService chatService;

    @Autowired
    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/conversation/{conversationId}")
    public Chat sendMessage(Chat chat, SimpMessageHeaderAccessor headerAccessor) {
        chatService.save(chat);
        return chat;
    }
}



