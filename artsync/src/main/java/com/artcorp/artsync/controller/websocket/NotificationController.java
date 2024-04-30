package com.artcorp.artsync.controller.websocket;

import com.artcorp.artsync.entity.Notification;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.ProjetService;
import com.artcorp.artsync.service.impl.ProjetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NotificationController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private ProjetService projetService;

    @Autowired
    public NotificationController(SimpMessagingTemplate simpMessagingTemplate, ProjetServiceImpl projetService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.projetService = projetService;
    }

    @MessageMapping("/notification/{userId}")
    @SendTo("/topic/notification/{userId}")
    public Notification notificationVise(Notification notif){
        return notif;
    }

    @MessageMapping("/notification/projet/{projetId}")
    @SendTo("/topic/notification/projet/{projetId}")
    public Notification notificationProjet(Notification notif, @DestinationVariable("projetId") Long projetId){
        List<Utilisateur> utilisateursProjet = projetService.getMembers(projetId);
        if (utilisateursProjet != null){
            utilisateursProjet.forEach(user ->{
                this.simpMessagingTemplate.convertAndSend("/topic/notification/"+user.getId(), notif);
            });
        }
        return notif;
    }
}
