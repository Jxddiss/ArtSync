package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private UtilisateurServiceImpl utilisateurService;

    @Autowired
    public AuthController(UtilisateurServiceImpl utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/authentification")
    public String connexion(@RequestParam("username") String username, @RequestParam("mdp") String mdp, HttpServletRequest request) {
        Utilisateur utilisateur = utilisateurService.connexion(username, mdp);

        if (utilisateur != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", utilisateur);
            return "redirect:/";
        }

        return "auth";
    }

    @GetMapping("/authentification")
    public String authentification() {
        return "auth";
    }

}
