package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.DemandeService;
import com.artcorp.artsync.service.ProjetService;
import com.artcorp.artsync.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupController {
    @Autowired
    UtilisateurService userService;
    @Autowired
    ProjetService projetService;
    @Autowired
    DemandeService demandeService;
    @GetMapping("/group/join")
    public String rejoindreGroup(@RequestParam("id") Long id, @RequestParam("type") String type, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Long idUtilisateur = null;
        try {
            Utilisateur utilisateur = ((Utilisateur) session.getAttribute("user"));
            idUtilisateur = utilisateur.getId();
        } catch (Exception e) {
            return "auth";
        }
        if ("rejoindre".equals(type)) {
            if (!projetService.findById(id).isPublique()) {
                demandeService.createDemande(idUtilisateur, id);
                return "recherche";
            }
            projetService.addUtilisateurToProjet(id, idUtilisateur);
        } else if ("quitter".equals(type)) {
            projetService.removeUtilisateurFromProjet(id, idUtilisateur);
        }
        return "recherche";
    }

    @GetMapping("/groupe")
    public String redirigerVersProjet() {
        return "groupe/group";
    }
}
