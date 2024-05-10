package com.artcorp.artsync.exception.domain;

import org.springframework.security.core.AuthenticationException;

public class MauvaisIdentifiantException extends AuthenticationException {
    public MauvaisIdentifiantException(String message) {
        super(message);
    }
}
