package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Annonce;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GroupController {
    @Autowired
    UtilisateurService userService;
    @Autowired
    ProjetService projetService;
    @Autowired
    DemandeService demandeService;
    @Autowired
    AnnonceService annonceService;
    @Autowired
    TacheService tacheService;
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

    @GetMapping("/groupe/group/{projetId}")
    public String redirigerVersProjet(@PathVariable("projetId") Long projectId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projectId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projectId));
        model.addAttribute("annonces", annonceService.findByProjetId(projectId));

        return "groupe/group";
    }

    @GetMapping("/groupe/group-users/{projetId}")
    public String getMembresProjet(@PathVariable("projetId") Long projectId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projectId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("membres", projetService.getMembers(projectId));
        model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projectId));
        model.addAttribute("annonces", annonceService.findByProjetId(projectId));

        return "groupe/group-users";
    }
    @GetMapping("/groupe/group-tache/{projetId}")
    public String getTacheProjet(@PathVariable("projetId") Long projectId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projectId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projectId));
        model.addAttribute("annonces", annonceService.findByProjetId(projectId));
        model.addAttribute("taches", tacheService.findByProjetId(projectId));

        return "groupe/group-tache";
    }
    @GetMapping("/groupe/group-demande/{projetId}")
    public String getDemandeProjet(@PathVariable("projetId") Long projectId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projectId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projectId));
        model.addAttribute("annonces", annonceService.findByProjetId(projectId));
        model.addAttribute("demandes", demandeService.findByProjetId(projectId));

        return "groupe/group-demande";
    }
    @GetMapping("/groupe/group-gestion/{projetId}")
    public String redirectGestion(@PathVariable("projetId") Long projectId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        if (!projetService.findById(projectId).getAdmin().getId().equals(utilisateur.getId())) {
            return "groupe/group";
        }
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projectId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projectId));
        model.addAttribute("annonces", annonceService.findByProjetId(projectId));

        return "groupe/group-gestion";
    }
    @PostMapping("groupe/group/{projetId}")
    public String handleAnnonceSubmission(@PathVariable Long projetId, @RequestParam("message") String message) {
        Annonce annonce = new Annonce();
        annonce.setProjet(projetService.findById(projetId));
        annonce.setUtilisateur(annonce.getProjet().getAdmin());
        annonce.setDateCreation(java.time.LocalDateTime.now());
        annonce.setMessage(message);
        annonceService.createAnnonce(annonce);
        return "redirect:/groupe/group/{projetId}";
    }

}
