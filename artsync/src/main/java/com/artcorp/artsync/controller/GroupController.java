package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/groupe/create/{projetId}")
    public String createTache(@PathVariable("projetId") Long projetId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("membres", projetService.getMembers(projetId));
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projetId));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        return "groupe/group-createTache";
    }

    @PostMapping("groupe/create/tache/{projetId}")
    public String createTache(@PathVariable Long projetId,
                              @RequestParam("titre") String titre,
                              @RequestParam("description") String description,
                              @RequestParam("etat") String etat,
                              @RequestParam("utilisateur") Long utilisateurId,
                              @RequestParam(value = "tacheId", defaultValue = "-1") Long tacheId,
                              Model model,
                              HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        Utilisateur user = userService.findById(utilisateurId);
        Tache tache = (tacheId != -1) ? tacheService.findById(tacheId) : new Tache();
        tache.setTitre(titre);
        tache.setDescription(description);
        tache.setEtat(etat);
        tache.setProjet(projetService.findById(projetId));
        tache.setUtilisateur(user);
        tache.setDateCreation(LocalDateTime.now());
        tache.setDateModification(LocalDateTime.now());

        if (tacheId != -1) {
            tacheService.updateTache(tache);
        } else {
            tacheService.createTache(tache);
        }
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projetId));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));

        return "redirect:/groupe/group-tache/{projetId}";
    }
    @GetMapping("groupe/group-tache/{projetId}/{tacheId}")
    public String deleteTask(@PathVariable Long projetId,
                             @PathVariable Long tacheId,
                             Model model,
                             HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projetId));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        tacheService.deleteTache(tacheId);

        return "redirect:/groupe/group-tache/{projetId}";
    }

    @GetMapping("groupe/group-tache/update/{projetId}/{tacheId}")
    public String updateTask(@PathVariable Long projetId,
                             @PathVariable Long tacheId,
                             Model model,
                             HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("membres", projetService.getMembers(projetId));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projetId));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        Tache tache = tacheService.findById(tacheId);
        model.addAttribute("tache", tache);

        return "groupe/group-createTache";
    }
    @GetMapping("groupe/group-demande/refuser/{projetId}/{demandeId}")
    public String refuserDemande(@PathVariable Long projetId,
                                 @PathVariable Long demandeId,
                                 Model model,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("membres", projetService.getMembers(projetId));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projetId));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        demandeService.deleteDemande(demandeId);

        return "redirect:/groupe/group-demande/{projetId}";
    }
    @GetMapping("groupe/group-demande/accepter/{projetId}/{demandeId}")
    public String accepterDemande(@PathVariable Long projetId,
                                  @PathVariable Long demandeId,
                                  Model model,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("membres", projetService.getMembers(projetId));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", projetService.getFileCount(projetId));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        Optional<Demande> demande = demandeService.findById(demandeId);
        Utilisateur userToAdd = demande.get().getUtilisateur();
        projetService.addUtilisateurToProjet(projetId, userToAdd.getId());
        demandeService.deleteDemande(demandeId);
        projetService.addUtilisateurToProjet(projetId, userToAdd.getId());
        return "redirect:/groupe/group-demande/{projetId}";
    }
}
