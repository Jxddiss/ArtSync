package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepos extends JpaRepository<Notification, Long> {
    List<Notification> findAllByLuAndIdDest(Boolean isLu, Long idDest);
}
