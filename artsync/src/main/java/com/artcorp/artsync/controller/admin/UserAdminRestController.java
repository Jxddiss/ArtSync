package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserAdminRestController {
    private final UtilisateurService utilisateurService;

    @Autowired
    public UserAdminRestController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<Utilisateur>> getAllUsers(){
        List<Utilisateur> listUtilisateur = utilisateurService.findAll();
        return new ResponseEntity<>(listUtilisateur, HttpStatus.OK);
    }
}
