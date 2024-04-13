package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.UtilisateurService;
import com.artcorp.artsync.service.ProjetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    UtilisateurService userService;
    @Autowired
    ProjetService projetService;

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
    public String redirigerVersRecherche(@RequestParam(value = "filtre", required = false) String filtre,
                                         @RequestParam(value = "type", required = false) String type,
                                         @RequestParam(value = "search", required = false) String search,
                                         Model model,
                                         HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = null;
        Long idUtilisateur = null;
        try {
            utilisateur = ((Utilisateur) session.getAttribute("user"));
            idUtilisateur = utilisateur.getId();
        } catch (Exception e) {
            //do nothing
        }


        if ("search".equals(type)) {
            if ("UTILISATEUR".equals(filtre)) {
                List<Utilisateur> listUtilisateurs = userService.findByKeyword(search);
                model.addAttribute("listUtilisateurs", listUtilisateurs);
                if (listUtilisateurs.size()<1){
                    model.addAttribute("message", "Aucun utilisateur trouvé avec le filtre '"+search+"'");
                }
            } else if ("GROUPE".equals(filtre)) {
                List<Projet> listProjets = projetService.findByKeyword(search);
                for (Projet projet : listProjets) {
                    if (utilisateur!=null && projet.getUtilisateurs().contains(userService.findById(idUtilisateur))) {
                        projet.setIn(true);
                    } else {
                        projet.setIn(false);
                    }
                }
                model.addAttribute("listProjets", listProjets);
                if (listProjets.size()<1){
                    model.addAttribute("message", "Aucun projet trouvé avec le filtre '"+search+"'");
                }
            } else {
                //ajoute stream ici quand on sera rendu la
            }
        } else {
            if ("UTILISATEUR".equals(filtre)) {
                List<Utilisateur> listUtilisateurs = userService.findAll();
                for (Utilisateur user : listUtilisateurs) {
                    if (utilisateur!=null && (user.getFollowers().contains(utilisateur) || user.getAmis().contains(utilisateur))) {
                        utilisateur.setIn(true);
                    } else {
                        utilisateur.setIn(false);
                    }
                }
                if (utilisateur!=null && listUtilisateurs.contains(utilisateur)) {
                    listUtilisateurs.remove(utilisateur);
                }
                model.addAttribute("listUtilisateurs", listUtilisateurs);
                if (listUtilisateurs.size()<1){
                    model.addAttribute("message", "Aucun utilisateur trouvé");
                }
            } else if ("GROUPE".equals(filtre)) {

                List<Projet> listProjets = projetService.findAll();

                for (Projet projet : listProjets) {
                    if (utilisateur!=null && projet.getUtilisateurs().contains(userService.findById(idUtilisateur))) {
                        projet.setIn(true);
                    } else {
                        projet.setIn(false);
                    }
                }
                model.addAttribute("listProjets", listProjets);
                if (listProjets.size()<1){
                    model.addAttribute("message", "Aucun projet trouvé");
                }
            } else {
                //ajoute stream ici quand on sera rendu la
            }
        }
        model.addAttribute("filtre", filtre);
        model.addAttribute("type", type);
        return "recherche";
    }
}
