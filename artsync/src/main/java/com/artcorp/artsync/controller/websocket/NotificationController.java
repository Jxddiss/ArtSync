package com.artcorp.artsync.controller.websocket;

import com.artcorp.artsync.entity.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/notification/{userId}")
    @SendTo("/topic/notification/{userId}")
    public Notification notificationVise(Notification notif){
        return notif;
    }
}
