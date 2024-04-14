package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Chat;
import com.artcorp.artsync.dto.ChatWebSocket;
import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.service.ChatService;
import com.artcorp.artsync.service.impl.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;

import static com.artcorp.artsync.constant.FileConstant.MEDIA_CHAT_BASE_FOLDER;

@Controller
public class ChatController {
    private ChatService chatService;

    @Autowired
    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/conversation/{conversationId}")
    public Chat sendMessage(ChatWebSocket chatWS, SimpMessageHeaderAccessor headerAccessor, String conversationId) throws IOException {
        Chat chat = chatWS.getChat();
        MultipartFile media = chatWS.getMedia();
        if (media != null){
            String originalFilename = StringUtils.cleanPath(media.getOriginalFilename());

            File parentDir = new File(MEDIA_CHAT_BASE_FOLDER);
            File saveFile = new File(parentDir.getAbsolutePath() + File.separator + conversationId + File.separator + originalFilename);
            saveFile.mkdirs();
            media.transferTo(saveFile);
            chat.setUrlMedia(saveFile.getAbsolutePath());
        }
        System.out.println(chat);
        chatService.save(chat);
        return chat;
    }
}



