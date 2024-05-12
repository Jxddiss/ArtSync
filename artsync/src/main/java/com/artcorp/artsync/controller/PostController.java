package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.exception.domain.FileFormatException;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.artcorp.artsync.service.NotificationService;
import com.artcorp.artsync.service.PostService;
import com.artcorp.artsync.service.UtilisateurService;
import com.artcorp.artsync.service.impl.NotificationServiceImpl;
import com.artcorp.artsync.service.impl.PostServiceImpl;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;
import static org.springframework.http.MediaType.*;

@Controller
public class PostController {
    private final PostService postService;
    private NotificationService notificationService;
    private UtilisateurService utilisateurService;

    @Autowired
    public PostController(PostServiceImpl postService,
                          NotificationServiceImpl notificationService,
                          UtilisateurServiceImpl utilisateurService) {
        this.postService = postService;
        this.notificationService = notificationService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/explorer")
    public String explorer(HttpServletRequest request,
                           Model model,
                           @AuthenticationPrincipal String username) throws NotConnectedException {


        HttpSession session = request.getSession();

        if(!username.equalsIgnoreCase("anonymousUser")){
            System.out.println(username);
            utilisateurService.addUserSessionIfNot(session,username);
        }

        List<Post> posts = null;
        if (session.getAttribute("user") == null) {
            posts = postService.findByPubliqueEnVedette(true);
        }else{
            posts = postService.findAllPosts();
        }
        model.addAttribute("posts", posts);
        return "explorer";
    }

    @GetMapping("/feed")
    @PreAuthorize("isAuthenticated()")
    public String feed(HttpServletRequest request,
                       Model model,
                       @AuthenticationPrincipal String username,
                       RedirectAttributes redirectAttributes) throws Exception {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
        List<Post> listPostsAbonnement = postService.findPostFollowing(utilisateur.getFollowing());
        List<Notification> listNotifications = notificationService.findAllUnreadNotificationByUserId(utilisateur.getId());
        listNotifications.forEach(notification -> {
            notification.setLu(true);
            notificationService.saveNotification(notification);
        });
        model.addAttribute("listNotifications",listNotifications);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("listPosts", listPostsAbonnement);
        return "utilisateur/feed";
    }



    @PostMapping("/ajouter-post")
    public String ajouterPost(HttpServletRequest request,
                              @AuthenticationPrincipal String username,
                             @RequestParam("file") MultipartFile image,
                             @RequestParam("description") String texte,
                             @RequestParam("type") String type,
                             @RequestParam("titre") String titre,
                             @RequestParam(name = "publique", required = false) boolean publique,
                             RedirectAttributes redirectAttributes) throws IOException, FileFormatException, NotConnectedException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
            if (image != null){

                if(image.getOriginalFilename() == null
                        || image.getOriginalFilename().isEmpty()
                        || !Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(image.getContentType())){
                    throw new FileFormatException("/utilisateur/profil/" + utilisateur.getPseudo());
                }

                String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
                FichierGeneral fichierGeneral = new FichierGeneral();
                fichierGeneral.setUrlMedia(originalFilename);

                Post post = new Post();
                post.setUtilisateur(utilisateur);
                post.setTitre(titre);
                post.setType(type);
                post.setTexte(texte);
                post.setPublique(publique);
                postService.savePost(image, utilisateur, originalFilename, fichierGeneral, post);
                redirectAttributes.addFlashAttribute("succes","Post ajouté avec succès");

            }
            return "redirect:/utilisateur/profil/" + utilisateur.getPseudo();
        }
        return "redirect:/authentification";
    }


}
