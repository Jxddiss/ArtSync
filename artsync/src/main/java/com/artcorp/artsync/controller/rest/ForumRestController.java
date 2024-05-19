package com.artcorp.artsync.controller.rest;

import com.artcorp.artsync.entity.Commentaire;
import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.CommentaireService;
import com.artcorp.artsync.service.FichierService;
import com.artcorp.artsync.service.ForumService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ForumRestController {

    private ForumService forumService;
    private CommentaireService commentaireService;
    @Autowired
    public ForumRestController(ForumService forumService, CommentaireService commentaireService) {
        this.forumService = forumService;
        this.commentaireService = commentaireService;
    }

    @DeleteMapping("/api/forum/delete")
    public String deleteForum(@Param("forumId") Long forumId, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        System.out.println("------------FORUM DELETED-------------");
        if (utilisateur!=null){
            Forum forum = forumService.findById(forumId);
            if (forum.getUtilisateur().getId().equals(utilisateur.getId())){
                forum.getListeCommentaires().forEach(commentaire -> {
                    commentaireService.deleteCommentaire(commentaire);
                });
                forumService.deleteForum(forum);
                return "Success";
            }

        }
        return "Failed";
    }

}
