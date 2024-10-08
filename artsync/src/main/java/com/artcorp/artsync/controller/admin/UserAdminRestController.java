package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.dto.HttpResponse;
import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestController
public class UserAdminRestController {
    private final UtilisateurService utilisateurService;
    private final CommentaireService commentaireService;
    private final ProjetService projetService;
    private final PostService postService;
    private final ForumService forumService;
    private final FichierService fichierService;

    @Autowired
    public UserAdminRestController(UtilisateurService utilisateurService, CommentaireService commentaireService, ProjetService projetService, PostService postService, ForumService forumService, FichierService fichierService) {
        this.utilisateurService = utilisateurService;
        this.commentaireService = commentaireService;
        this.projetService = projetService;
        this.postService = postService;
        this.forumService = forumService;
        this.fichierService = fichierService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<Utilisateur>> getAllUsers(){
        List<Utilisateur> listUtilisateur = utilisateurService.findAllAdmin();
        return new ResponseEntity<>(listUtilisateur, HttpStatus.OK);
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<Utilisateur> getOneUsers(@PathVariable("userId") Long userId, HttpMethod method) throws NoResourceFoundException {
        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur == null){
            throw new NoResourceFoundException(method,"Utilisateur id: "+userId);
        }
        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }

    @GetMapping("/api/users/commentaires/{userId}")
    public ResponseEntity<List<Commentaire>> getCommentaireByOneUser(@PathVariable("userId") Long userId,
                                                                     HttpMethod method) throws NoResourceFoundException {
        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur == null){
            throw new NoResourceFoundException(method,"Utilisateur id: "+userId);
        }
        List<Commentaire> listCommentaires = commentaireService.findAllByUser(utilisateur);
        return new ResponseEntity<>(listCommentaires,HttpStatus.OK);
    }

    @GetMapping("/api/users/groups/{userId}")
    public ResponseEntity<List<Projet>> getGroupsByOneUser(@PathVariable("userId") Long userId,
                                                                     HttpMethod method) throws NoResourceFoundException {
        List<Projet> listProjets = projetService.findProjectsOfUser(userId);
        return new ResponseEntity<>(listProjets,HttpStatus.OK);
    }

    @GetMapping("/api/users/posts/{userId}")
    public ResponseEntity<List<Post>> getPostsByOneUser(@PathVariable("userId") Long userId,
                                                        HttpMethod method) throws NoResourceFoundException {
        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur == null){
            throw new NoResourceFoundException(method,"Utilisateur id: "+userId);
        }
        List<Post> listPost = postService.findPostByUser(utilisateur);
        return new ResponseEntity<>(listPost,HttpStatus.OK);
    }

    @GetMapping("/api/users/fichiers/{userId}")
    public ResponseEntity<List<FichierGeneral>> getFichiersByOneUser(@PathVariable("userId") Long userId,
                                                                     HttpMethod method) throws NoResourceFoundException {
        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur == null){
            throw new NoResourceFoundException(method,"Utilisateur id: "+userId);
        }
        List<FichierGeneral> listFichiers = fichierService.findAllByUtilisateur(utilisateur);
        return new ResponseEntity<>(listFichiers,HttpStatus.OK);
    }

    @GetMapping("/api/users/forums/{userId}")
    public ResponseEntity<List<Forum>> getForumsByOneUser(@PathVariable("userId") Long userId,
                                                         HttpMethod method) throws NoResourceFoundException {
        Utilisateur utilisateur = utilisateurService.findById(userId);
        if (utilisateur == null){
            throw new NoResourceFoundException(method,"Utilisateur id: "+userId);
        }
        List<Forum> listPost = forumService.findAllByUtilisateur(utilisateur);
        return new ResponseEntity<>(listPost,HttpStatus.OK);
    }



    @DeleteMapping("/api/users/delete")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@RequestParam("userId") Long userId){
        utilisateurService.delete(userId);
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK.value(),
                HttpStatus.OK,HttpStatus.OK.getReasonPhrase(), "Utilisateur supprimé id : "+ userId);
        return new ResponseEntity<>(httpResponse,HttpStatus.OK);
    }

    @PutMapping("/api/users/update")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public ResponseEntity<Utilisateur> updateUser(@RequestBody Utilisateur utilisateur){
        Utilisateur savedUser = utilisateurService.findByPseudo(utilisateur.getPseudo());
        savedUser.setRole(utilisateur.getRole());
        savedUser.setActive(utilisateur.isActive());
        utilisateurService.update(savedUser);
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK.value(),
                HttpStatus.OK,HttpStatus.OK.getReasonPhrase(), "Utilisateur modifié id : "+ utilisateur.getId());
        return new ResponseEntity<>(savedUser,HttpStatus.OK);
    }
}
