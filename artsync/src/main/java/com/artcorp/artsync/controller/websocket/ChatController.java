package com.artcorp.artsync.controller.websocket;

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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.artcorp.artsync.constant.FileConstant.RELATIVE_PATH;

@Controller
public class ChatController {
    private ChatService chatService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat/is-writing/{conversationId}/{userPseudo}")
    @SendTo("/topic/chat/is-writing/{conversationId}/{userPseudo}")
    public boolean isTyping(boolean isWriting){
        return isWriting;
    }

    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/conversation/{conversationId}")
    public Chat sendMessage(Chat chat) {
        if (!StringUtils.isEmpty(chat.getUrlMedia())) {
            chat.setUrlMedia(RELATIVE_PATH  + chat.getUrlMedia());
        }
        chat.setDateTimeEnvoie(LocalDateTime.now());
        return chatService.save(chat);
    }

    @MessageMapping("/chat/appel/add/{conversationId}")
    public void appel(String userId, @DestinationVariable String conversationId) {
        LOGGER.info("Appel : " + userId);
        LOGGER.info("Conversation : " + conversationId);
    }

    @MessageMapping("/chat/appel/call/{conversationId}/{userId}")
    @SendTo("/topic/appel/call/{conversationId}/{userId}")
    public String call(String call) {
        LOGGER.info("Call : " + call);
        return call;
    }

    @MessageMapping("/chat/appel/offer/{conversationId}/{userId}")
    @SendTo("/topic/appel/offer/{conversationId}/{userId}")
    public String offer(String offer) {
        return offer;
    }

    @MessageMapping("/chat/appel/answer/{conversationId}/{userId}")
    @SendTo("/topic/appel/answer/{conversationId}/{userId}")
    public String answer(String answer) {
        return answer;
    }

    @MessageMapping("/chat/appel/candidate/{conversationId}/{userId}")
    @SendTo("/topic/appel/candidate/{conversationId}/{userId}")
    public String candidate(String candidate) {
        return candidate;
    }

    @MessageMapping("/chat/appel/remove/{conversationId}")
    @SendTo("/topic/appel/remove/{conversationId}")
    public String remove(String idUserEtIdAmi) {
        LOGGER.info("idUserEtIdAmi Deconnexion : " + idUserEtIdAmi);
        return "{message : 'disconnected'}";
    }
}



