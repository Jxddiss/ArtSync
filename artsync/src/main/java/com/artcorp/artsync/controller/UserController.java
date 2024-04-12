package com.artcorp.artsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/conversation")
    public String redirigerVersConversation() {
        return "utilisateur/conversation";
    }
    @GetMapping("/feed")
    public String redirigerVersFeed() {
        return "utilisateur/feed";
    }
    @GetMapping("/profil")
    public String redirigerVersProfil() {
        return "utilisateur/profile";
    }
    @GetMapping("/relation")
    public String redirigerVersRelation() {
        return "utilisateur/relation";
    }
    @GetMapping("/portfolio")
    public String redirigerVersPortfolio() {
        return "utilisateur/portfolio";
    }
}
