package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ConfirmationTokenRepos extends JpaRepository<ConfirmationToken, Long> {

    @Query("SELECT c FROM ConfirmationToken c WHERE c.userId = ?1 AND c.dateExpiration > ?2 AND c.type = 'PASSWORD_RESET'")
    ConfirmationToken findByUserIdReset(Long userId, Date dateCourrate);

}
