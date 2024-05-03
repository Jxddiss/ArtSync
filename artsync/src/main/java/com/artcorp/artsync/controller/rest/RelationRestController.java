package com.artcorp.artsync.controller.rest;


import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.service.DemandeService;
import com.artcorp.artsync.service.NotificationService;
import com.artcorp.artsync.service.ProjetService;
import com.artcorp.artsync.service.UtilisateurService;
import com.artcorp.artsync.service.impl.NotificationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationRestController {
    private UtilisateurService utilisateurService;
    private ProjetService projetService;
    private DemandeService demandeService;
    private SimpMessagingTemplate simpMessagingTemplate;
    private NotificationService notificationService;

    @Autowired
    public RelationRestController(UtilisateurService utilisateurService,
                                  ProjetService projetService,
                                  DemandeService demandeService,
                                  SimpMessagingTemplate simpMessagingTemplate,
                                  NotificationServiceImpl notificationService) {
        this.utilisateurService = utilisateurService;
        this.projetService = projetService;
        this.demandeService = demandeService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
    }

    @PostMapping("/recherche/updateRelationUsr")
    public String updateRelationUser(@Param("userId") Long userId, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "false";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        Notification notif = utilisateurService.updateRelations(userId,utilisateur.getId());
        utilisateur = utilisateurService.findById(utilisateur.getId());
        session.setAttribute("user",utilisateur);

        if(notif != null){
            this.simpMessagingTemplate.convertAndSend("/topic/notification/"+userId,
                    notificationService.saveNotification(notif));
        }

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
