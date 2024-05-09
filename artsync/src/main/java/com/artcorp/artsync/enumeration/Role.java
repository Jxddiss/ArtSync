package com.artcorp.artsync.enumeration;


import static com.artcorp.artsync.constant.Authority.*;

public enum Role {
    ROLE_USER(USERS_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
