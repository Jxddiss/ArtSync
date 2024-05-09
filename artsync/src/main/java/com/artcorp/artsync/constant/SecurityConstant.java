package com.artcorp.artsync.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000;// 5 days date expiration (milliseconds)
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFED = "Token cannot be verified";
    public static final String ARTSYNC = "ArtSync";
    public static final String ARTSYNC_ADMINISTRATION = "User Management Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to acces this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/","/favicon.ico","/fonts/*","/css/*","/js/*",
            "/index","/authentification", "/login", "/media/**","/recherche","/utilisateur/profil/*", "/classement","/explorer"};
    //public static final String[] PUBLIC_URLS = {"**"};
}
