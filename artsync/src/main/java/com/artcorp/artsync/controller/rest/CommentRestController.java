package com.artcorp.artsync.controller.rest;

import com.artcorp.artsync.entity.Commentaire;
import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.CommentaireService;
import com.artcorp.artsync.service.ForumService;
import com.artcorp.artsync.service.impl.PostServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentRestController {
    @Autowired
    private CommentaireService commentaireService;
    @Autowired
    private ForumService forumService;
    @Autowired
    private PostServiceImpl postService;

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

    @PostMapping("/post/comment")
    public String commenterPost(@Param("comment") String comment,@Param("postId") Long postId, HttpServletRequest request){
        HttpSession session = request.getSession(false);

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

        Post post = postService.findById(postId);

        Commentaire commentaire = new Commentaire();
        commentaire.setMessage(comment);
        commentaire.setUtilisateur(utilisateur);
        commentaire.setPost(post);
        if(comment != null){
            commentaireService.save(commentaire);
            return "true";
        }
        return "false";
    }

    @PostMapping("/post/like")
    public String updatePost(@Param("like") String like, @Param("postId") Long postId){
        if(like.equalsIgnoreCase("like")){
            postService.likePost(postId);
            return "true";
        }else if(like.equalsIgnoreCase("unlike")){
            postService.unLikePost(postId);
            return "true";
        }
        return "false";
    }

    @DeleteMapping("/commentaire/delete")
    public String deleteCommentaire(@Param("commentaireId") Long commentaireId, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        if (utilisateur!=null){
            Commentaire commentaire = commentaireService.findById(commentaireId);
            if ((commentaire.getUtilisateur().equals(utilisateur))
                    || (commentaire.getForum() != null && commentaire.getForum().getUtilisateur().equals(utilisateur)
                    || (commentaire.getPost() != null && commentaire.getPost().getUtilisateur().equals(utilisateur)))){
                commentaireService.deleteCommentaire(commentaire);
                return "Success";
            }
        }
        return "Failed";
    }

}