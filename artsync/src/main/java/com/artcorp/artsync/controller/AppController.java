package com.artcorp.artsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/index")
    public String redirigerVersIndex() {
        return "index";
    }
    @GetMapping("/explorer")
    public String redirigerVersExplorer() {
        return "explorer";
    }
    @GetMapping("/idee")
    public String redirigerVersIdee() {
        return "boite-idee";
    }
    @GetMapping("/classement")
    public String redirigerVersClassement() {
        return "classement";
    }
    @GetMapping("/recherche")
    public String redirigerVersRecherche() {
        return "recherche";
    }
}
