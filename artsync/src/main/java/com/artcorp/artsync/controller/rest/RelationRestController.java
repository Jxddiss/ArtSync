package com.artcorp.artsync.controller.rest;


import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.service.DemandeService;
import com.artcorp.artsync.service.ProjetService;
import com.artcorp.artsync.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationRestController {
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    ProjetService projetService;
    @Autowired
    DemandeService demandeService;

    @PostMapping("/recherche/updateRelationUsr")
    public String updateRelationUser(@Param("userId") Long userId, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "false";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        utilisateurService.updateRelations(userId,utilisateur.getId());
        utilisateur = utilisateurService.findById(utilisateur.getId());
        session.setAttribute("user",utilisateur);
        return "true";
    }
    @PostMapping("/recherche/updateRelationGrp")
    public String updateRelationProject (@Param("projetId") Long projetId,@Param("operation") String operation, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "Pas d'utilisateur";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        if (operation.equals("add")){
            if (!projetService.findById(projetId).isPublique()){
                for (Demande demande:demandeService.findByUtilisateurId(utilisateur.getId())){
                    if (demande.getProjet().equals(projetService.findById(projetId))){
                        return "Demande déjà en cours.";
                    }
                }
                demandeService.createDemande(utilisateur.getId(),projetId);
                return "Demande envoyée";
            }
            projetService.addUtilisateurToProjet(projetId,utilisateur.getId());
            return "Ajouté au groupe";
        }else{
            projetService.removeUtilisateurFromProjet(projetId,utilisateur.getId());
            return "Supprimé du groupe";
        }
    }
}
