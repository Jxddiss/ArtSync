package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Chat;
import com.artcorp.artsync.service.ChatService;
import com.artcorp.artsync.service.impl.ChatServiceImpl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

import static com.artcorp.artsync.constant.FileConstant.RELATIVE_PATH;

@Controller
public class ChatController {
    private ChatService chatService;
    private SimpMessagingTemplate simpMessagingTemplate;
    private Set<String> utilisateursConnecte = new HashSet<>();
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public ChatController(ChatServiceImpl chatService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
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

    @MessageMapping("/chat/appel/add/{conversationId}")
    public void appel(String userId, @DestinationVariable String conversationId) {
        LOGGER.info("Appel : " + userId);
        utilisateursConnecte.add(userId);
        for (String user : utilisateursConnecte) {
            LOGGER.info("user : " + user);
        }
        LOGGER.info("Conversation : " + conversationId);
    }

    @MessageMapping("/chat/appel/call/{userId}")
    @SendTo("/topic/appel/call/{userId}")
    public String call(String call) {
        LOGGER.info("Call : " + call);
        return call;
    }

    @MessageMapping("/chat/appel/offer/{userId}")
    @SendTo("/topic/appel/offer/{userId}")
    public String offer(String offer) {
        LOGGER.info("Offer came : " + offer);
        LOGGER.info("Offer : " + offer);
        return offer;
    }

    @MessageMapping("/chat/appel/answer/{userId}")
    @SendTo("/topic/appel/answer/{userId}")
    public String answer(String answer) {
        LOGGER.info("Answer came : " + answer);
        LOGGER.info("Answer : " + answer);
        LOGGER.info("Answer sent");
        return answer;
    }

    @MessageMapping("/chat/appel/candidate/{userId}")
    @SendTo("/topic/appel/candidate/{userId}")
    public String candidate(String candidate) {
        LOGGER.info("Candidate came : " + candidate);
        LOGGER.info("Candidate : " + candidate);
        return candidate;
    }

}



