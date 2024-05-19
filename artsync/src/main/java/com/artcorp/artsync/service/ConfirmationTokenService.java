package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.ConfirmationToken;

import java.util.Date;

public interface ConfirmationTokenService {

    ConfirmationToken findByUserIdReset(Long userId, Date dateCourrate);

    void save(ConfirmationToken confirmationToken);

    void delete(ConfirmationToken confirmationToken);
}
