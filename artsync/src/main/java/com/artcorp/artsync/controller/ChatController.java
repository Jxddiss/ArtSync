package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Chat;
import com.artcorp.artsync.service.ChatService;
import com.artcorp.artsync.service.impl.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import static com.artcorp.artsync.constant.FileConstant.RELATIVE_PATH;

@Controller
public class ChatController {
    private ChatService chatService;

    @Autowired
    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/conversation/{conversationId}")
    public Chat sendMessage(Chat chat) {
        if (!StringUtils.isEmpty(chat.getUrlMedia())) {
            chat.setUrlMedia(RELATIVE_PATH  + chat.getUrlMedia());
        }
        chatService.save(chat);
        return chat;
    }


}



