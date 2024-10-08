package com.artcorp.artsync.controller.websocket;

import com.artcorp.artsync.entity.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class ChatVideoGroupeController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @MessageMapping("/chat/appel/groupe/new/{conversationId}")
    @SendTo("/topic/appel/groupe/new/{conversationId}")
    public Utilisateur joinCallGroup(Utilisateur user) {
        return user;
    }

    @MessageMapping("/chat/appel/groupe/add/{conversationId}/{idUser}")
    @SendTo("/topic/appel/groupe/add/{conversationId}/{idUser}")
    public Utilisateur getCurrentUsers(Utilisateur user) {
        return user;
    }

    @MessageMapping("/chat/appel/groupe/offer/{conversationId}/{userId}/{userId2}")
    @SendTo("/topic/appel/groupe/offer/{conversationId}/{userId}/{userId2}")
    public String offer(String offer) {
        return offer;
    }

    @MessageMapping("/chat/appel/groupe/answer/{conversationId}/{userId}/{userId2}")
    @SendTo("/topic/appel/groupe/answer/{conversationId}/{userId}/{userId2}")
    public String answer(String answer) {
        return answer;
    }

    @MessageMapping("/chat/appel/groupe/candidate/{conversationId}/{userId}/{userId2}")
    @SendTo("/topic/appel/groupe/candidate/{conversationId}/{userId}/{userId2}")
    public String candidate(String candidate) {
        return candidate;
    }

    @MessageMapping("/chat/appel/groupe/ready/{conversationId}/{userId}/{userId2}")
    @SendTo("/topic/appel/groupe/ready/{conversationId}/{userId}/{userId2}")
    public String ready(String candidate) {
        return candidate;
    }

    @MessageMapping("/chat/appel/groupe/leave/{conversationId}")
    @SendTo("/topic/appel/groupe/leave/{conversationId}")
    public String leave(String userId){
        return userId;
    }


}



