package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<Utilisateur> getOneUsers(@PathVariable("userId") Long userId, HttpMethod method) throws NoResourceFoundException {
        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur == null){
            throw new NoResourceFoundException(method,"Utilisateur id: "+userId);
        }
        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }
}
