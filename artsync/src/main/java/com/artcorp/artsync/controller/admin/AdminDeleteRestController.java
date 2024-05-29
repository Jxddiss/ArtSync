package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.dto.HttpResponse;
import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestController
@PreAuthorize("hasAnyAuthority('user:delete')")
public class AdminDeleteRestController {
    private final CommentaireService commentaireService;
    private final PostService postService;
    private final FichierService fichierService;
    private final ForumService forumService;
    private final ProjetService projetService;
    private final AnnonceService annonceService;
    private final TacheService tacheService;
    private final ConversationService conversationService;
    private final ChatService chatService;
    private final DemandeService demandeService;

    @Autowired
    public AdminDeleteRestController(CommentaireService commentaireService,
                                     PostService postService,
                                     FichierService fichierService,
                                     ForumService forumService, ProjetService projetService, AnnonceService annonceService, TacheService tacheService, ConversationService conversationService, ChatService chatService, DemandeService demandeService) {
        this.commentaireService = commentaireService;
        this.postService = postService;
        this.fichierService = fichierService;
        this.forumService = forumService;
        this.projetService = projetService;
        this.annonceService = annonceService;
        this.tacheService = tacheService;
        this.conversationService = conversationService;
        this.chatService = chatService;
        this.demandeService = demandeService;
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

    @DeleteMapping("/api/admin/groups/delete")
    public ResponseEntity<HttpResponse> supprimerProjet(@RequestParam("projetId") Long projetId,
                                  HttpMethod method) throws NoResourceFoundException{
        Projet projet = projetService.findById(projetId);
        if (projet == null){
            throw new NoResourceFoundException(method,"Projet id: "+projetId);
        }
        List<Utilisateur> users = projetService.getMembers(projetId);
        for (Utilisateur user : users) {
            projetService.removeUtilisateurFromProjet(projetId, user.getId());
        }
        fichierService.deleteAllByProjet(projet);
        annonceService.deleteAllByProjetId(projetId);
        tacheService.deleteAllByProjetId(projetId);
        demandeService.deleteAllByProjetId(projetId);
        chatService.deleteAllByConversationId(conversationService.findByProjet(projet).getId());
        conversationService.deleteAllByProjetId(projetId);
        projetService.deleteProjet(projetId);
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK.value(),
                HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),"Success" );

        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/fichiers/delete")
    public ResponseEntity<HttpResponse> deleteFichier(@RequestParam("fichierId") Long fichierId){
        fichierService.deleteById(fichierId,"groupe");
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK.value(),
                HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),"Success" );

        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }
}
