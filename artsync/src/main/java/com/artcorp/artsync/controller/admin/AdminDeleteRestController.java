package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.dto.HttpResponse;
import com.artcorp.artsync.entity.Commentaire;
import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.CommentaireService;
import com.artcorp.artsync.service.FichierService;
import com.artcorp.artsync.service.ForumService;
import com.artcorp.artsync.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestController
@PreAuthorize("hasAnyAuthority('user:delete')")
public class AdminDeleteRestController {
    private final CommentaireService commentaireService;
    private final PostService postService;
    private final FichierService fichierService;
    private final ForumService forumService;

    @Autowired
    public AdminDeleteRestController(CommentaireService commentaireService,
                                     PostService postService,
                                     FichierService fichierService,
                                     ForumService forumService) {
        this.commentaireService = commentaireService;
        this.postService = postService;
        this.fichierService = fichierService;
        this.forumService = forumService;
    }

    @DeleteMapping("/api/admin/commentaires/delete")
    public ResponseEntity<HttpResponse> deleteCommentaire(@RequestParam("commentaireId") Long commentaireId,
                                                    HttpMethod method) throws NoResourceFoundException {
        Commentaire commentaire = commentaireService.findById(commentaireId);

        if (commentaire!=null){
            commentaireService.deleteCommentaire(commentaire);
            HttpResponse httpResponse = new HttpResponse(HttpStatus.OK.value(),
                    HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),"Success" );

            return new ResponseEntity<>(httpResponse, HttpStatus.OK);
        }
        throw new NoResourceFoundException(method,"Commentaire id: "+commentaireId);
    }

    @DeleteMapping("/api/admin/posts/delete")
    private ResponseEntity<HttpResponse> deletePost(@RequestParam("postId") Long postId,
                                                    HttpMethod method) throws NoResourceFoundException{
        Post post = postService.findById(postId);
        if (post == null){
            throw new NoResourceFoundException(method,"Post id: "+postId);
        }
        post.getListeFichiers().forEach(fichierGeneral -> {
            fichierService.deleteById(fichierGeneral.getId(),"post");
        });
        postService.deletePost(post);
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK.value(),
                HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),"Success" );

        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/forums/delete")
    public ResponseEntity<HttpResponse> deleteForum(@RequestParam("forumId") Long forumId,
                              HttpMethod method) throws NoResourceFoundException{
        Forum forum = forumService.findById(forumId);

        if (forum == null){
            throw new NoResourceFoundException(method,"Forum id: "+forumId);
        }

        forum.getListeCommentaires().forEach(commentaire -> {
            commentaireService.deleteCommentaire(commentaire);
        });
        forumService.deleteForum(forum);
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK.value(),
                HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),"Success" );

        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }
}
