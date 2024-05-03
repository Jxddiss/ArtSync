package com.artcorp.artsync.controller.websocket;

import com.artcorp.artsync.entity.Notification;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.NotificationService;
import com.artcorp.artsync.service.ProjetService;
import com.artcorp.artsync.service.impl.NotificationServiceImpl;
import com.artcorp.artsync.service.impl.ProjetServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class NotificationController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private ProjetService projetService;
    private NotificationService notificationService;

    @Autowired
    public NotificationController(SimpMessagingTemplate simpMessagingTemplate, ProjetServiceImpl projetService, NotificationServiceImpl notificationService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.projetService = projetService;
        this.notificationService = notificationService;
    }

    @PostMapping("/notification/set-lu")
    public void setNotificationLu(@Param("id") Long id){
        Notification notification = notificationService.findById(id);
        if(notification != null){
            notification.setLu(true);
            notificationService.saveNotification(notification);
        }
    }

    @MessageMapping("/notification/{userId}")
    @SendTo("/topic/notification/{userId}")
    public Notification notificationVise(Notification notif, @DestinationVariable("userId") Long userId){
        notif.setLu(false);
        notif.setIdDest(userId);
        return notificationService.saveNotification(notif);
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

    @MessageMapping("/notification/post/{userId}")
    public void notificationPost(Notification notification, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

        if (utilisateur != null){
            utilisateur.getFollowers().forEach(user ->{
                this.simpMessagingTemplate.convertAndSend("/topic/notification/"+user.getId(), notification);
            });
        }
    }
}
