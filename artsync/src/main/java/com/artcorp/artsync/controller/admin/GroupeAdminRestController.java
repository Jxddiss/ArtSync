package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.service.ProjetService;
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
public class GroupeAdminRestController {
    private final ProjetService projetService;

    @Autowired
    public GroupeAdminRestController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @GetMapping("/api/groups")
    public ResponseEntity<List<Projet>> getAllGroupeAdmin(){
        List<Projet> listeProjet = projetService.findAll();
        return new ResponseEntity<>(listeProjet, HttpStatus.OK);
    }

    @GetMapping("/api/groups/{groupeId}")
    public ResponseEntity<Projet> getOneGroupeAdmin(@PathVariable("groupeId") Long groupId, HttpMethod method) throws NoResourceFoundException {
        Projet projet = projetService.findById(groupId);
        if (projet == null){
            throw new NoResourceFoundException(method,"Projet : "+groupId);
        }
        return new ResponseEntity<>(projet, HttpStatus.OK);
    }
}
