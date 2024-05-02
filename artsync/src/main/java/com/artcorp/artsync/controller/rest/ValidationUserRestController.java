package com.artcorp.artsync.controller.rest;

import com.artcorp.artsync.service.UtilisateurService;
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
}
