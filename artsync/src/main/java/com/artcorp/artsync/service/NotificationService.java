package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification findById(Long id);
    List<Notification> findAllUnreadNotificationByUserId(Long userId);
    Notification saveNotification(Notification notification);
}
