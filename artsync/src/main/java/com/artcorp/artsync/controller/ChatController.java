package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Chat;
import com.artcorp.artsync.dto.ChatWebSocket;
import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.service.ChatService;
import com.artcorp.artsync.service.impl.ChatServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashSet;

import static com.artcorp.artsync.constant.FileConstant.MEDIA_CHAT_BASE_FOLDER;
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



