package com.artcorp.artsync.controller.rest;


import com.artcorp.artsync.entity.Commentaire;
import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Utilisateur;
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

    @PostMapping("/recherche/updateRelationUsr")
    public String rechercheUtilisateur(@Param("userId") Long userId, HttpServletRequest request){
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
}
