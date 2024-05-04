package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Notification;
import com.artcorp.artsync.repos.NotificationRepos;
import com.artcorp.artsync.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepos notificationRepos;

    @Autowired
    public NotificationServiceImpl(NotificationRepos notificationRepos) {
        this.notificationRepos = notificationRepos;
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepos.findById(id).isPresent() ? notificationRepos.findById(id).get() : null;
    }

    @Override
    public List<Notification> findAllUnreadNotificationByUserId(Long userId) {
        return notificationRepos.findAllByLuAndIdDest(false,userId);
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepos.save(notification);
    }
}
