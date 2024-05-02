package com.artcorp.artsync.controller.rest;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationUserRestController {
    private UtilisateurService utilisateurService;

    @Autowired
    public ValidationUserRestController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/api/utilisateur/check-email")
    public String checkEmail(@Param("email") String email, @Param("id") Long userId){

        return this.utilisateurService.emailIsValid(email,userId) ? "true" : "false";
    }

    @PostMapping("/api/utilisateur/check-pseudo")
    public String checkPseudo(@Param("pseudo") String pseudo, @Param("id") Long userId){
        return this.utilisateurService.pseudoIsValid(pseudo,userId) ? "true" : "false";
    }
    @PostMapping("/api/update/user-background")
    public String updateUserBackground(@Param("backgroundColor") String backgroundColor, @Param("backgroundTexture") String backgroundTexture, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        if (!backgroundColor.equals("")){
            utilisateur.setBackgroundColor(backgroundColor);
        }
        if(!backgroundTexture.equals("")){
            utilisateur.setBackgroundTexture(backgroundTexture);
        }
        utilisateurService.update(utilisateur);
        session.setAttribute("user",utilisateur);
        return "true";
    }
}
