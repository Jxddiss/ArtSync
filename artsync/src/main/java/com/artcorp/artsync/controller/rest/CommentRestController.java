package com.artcorp.artsync.controller.rest;

import com.artcorp.artsync.entity.Commentaire;
import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.CommentaireService;
import com.artcorp.artsync.service.ForumService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentRestController {
    @Autowired
    private CommentaireService commentaireService;
    @Autowired
    private ForumService forumService;

    @PostMapping("/forum/comment")
    public String rechercheUtilisateur(@Param("comment") String comment,@Param("forumId") Long forumId, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

        Forum forum = forumService.findById(forumId);

        Commentaire commentaire = new Commentaire();
        commentaire.setForum(forum);
        commentaire.setMessage(comment);
        commentaire.setUtilisateur(utilisateur);
        if(comment != null){
            commentaireService.save(commentaire);
            return "true";
        }
        return "false";
    }

}