package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.artcorp.artsync.repos.ProjetRepos;
import com.artcorp.artsync.service.impl.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;

@Controller
public class UserController {
    private final UtilisateurServiceImpl utilisateurService;
    private final ProjetRepos projetRepos;
    private final PostServiceImpl postService;
    private final PortfolioServiceImpl portfolioService;
    private final ConversationServiceImpl conversationService;
    private final ChatServiceImpl chatService;
    private final ProjetServiceImpl projetService;
    private final TacheServiceImpl tacheService;
    private final AnnonceServiceImpl annonceService;
    private final FichierServiceImpl fichierService;
    private final DemandeServiceImpl demandeService;
    private final ForumServiceImpl forumService;
    private final CommentaireServiceImpl commentaireService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UtilisateurServiceImpl utilisateurService, ProjetRepos projetRepos, PostServiceImpl postService, PortfolioServiceImpl portfolioService, ConversationServiceImpl conversationService, ChatServiceImpl chatService, ProjetServiceImpl projetService, TacheServiceImpl tacheService, AnnonceServiceImpl annonceService, FichierServiceImpl fichierService, DemandeServiceImpl demandeService, ForumServiceImpl forumService, CommentaireServiceImpl commentaireService, BCryptPasswordEncoder passwordEncoder) {
        this.utilisateurService = utilisateurService;
        this.projetRepos = projetRepos;
        this.postService = postService;
        this.portfolioService = portfolioService;
        this.conversationService = conversationService;
        this.chatService = chatService;
        this.projetService = projetService;
        this.tacheService = tacheService;
        this.annonceService = annonceService;
        this.fichierService = fichierService;
        this.demandeService = demandeService;
        this.forumService = forumService;
        this.commentaireService = commentaireService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/utilisateur/profil/{pseudo}")
    public String redirigerVersProfil(@PathVariable("pseudo") String pseudo,
                                      Model model,
                                      @AuthenticationPrincipal String username,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) throws NotConnectedException {
        HttpSession session = request.getSession(false);


        if (pseudo != null) {
            Utilisateur utilisateur = utilisateurService.findByPseudo(pseudo);
            if (utilisateur != null) {
                if (session!=null){
                    Utilisateur userSess;
                    if(!username.equalsIgnoreCase("anonymousUser")){
                        userSess = utilisateurService.addUserSessionIfNot(session, username);
                    }

                    userSess = (Utilisateur) session.getAttribute("user");
                    List<Post> listPosts = postService.findPostByUser(utilisateur);
                    model.addAttribute("listPosts", listPosts);
                    if (userSess!=null){
                        for (Utilisateur follower: utilisateur.getFollowers()){
                            if (follower.getPseudo().equals(userSess.getPseudo())){
                                utilisateur.setIn(true);
                                break;
                            }
                        }
                    }
                } else{
                    List<Post> listPosts = postService.findAllByUtilisateurAndPublique(utilisateur);
                    model.addAttribute("listPosts", listPosts);
                }

                Post banniere = postService.findBanniereUtilisateur(utilisateur);
                int nbAbonnes = utilisateur.getFollowers().size();
                int nbAbonnements = utilisateur.getFollowing().size();
                Portfolio portfolio = portfolioService.findByUtilisateur(utilisateur);
                model.addAttribute("utilisateur", utilisateur);
                model.addAttribute("banniere", banniere);
                model.addAttribute("nbAbonnes", nbAbonnes);
                model.addAttribute("nbAbonnements", nbAbonnements);
                model.addAttribute("portfolio", portfolio);
                return "utilisateur/profil";
            }
            redirectAttributes.addFlashAttribute("error", "Cet utilisateur n'existe pas");
            return "redirect:/utilisateur/feed";
        }
        redirectAttributes.addFlashAttribute("error", "Aucun utilisateur trouvé");
        return "redirect:/index";
    }
    @GetMapping("/utilisateur/relation")
    public String redirigerVersRelation(HttpServletRequest request,
                                        @AuthenticationPrincipal String username,
                                        Model model,
                                        RedirectAttributes redirectAttributes) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
            if (utilisateur != null) {
                Set<Utilisateur> following = utilisateur.getFollowing();
                model.addAttribute("projetCount",projetService.findProjectsOfUser(utilisateur.getId()).size());
                model.addAttribute("type", "Abonnements");
                model.addAttribute("following", following);
                model.addAttribute("posts",postService.findAllPostOfFriends(utilisateur));
                return "utilisateur/relation";
            }
            redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
            return "redirect:/authentification";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }

    @GetMapping("/utilisateur/relation/groupes")
    public String redirigerVersRelationGroupes(HttpServletRequest request,
                                               @AuthenticationPrincipal String username,
                                               Model model,
                                               RedirectAttributes redirectAttributes) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
            List<Projet> groupes = projetRepos.findByUtilisateursId(utilisateur.getId());
            model.addAttribute("projetCount",projetService.findProjectsOfUser(utilisateur.getId()).size());
            model.addAttribute("type", "Groupes");
            model.addAttribute("projets", groupes);
            model.addAttribute("posts",postService.findAllPostOfFriends(utilisateur));
            return "utilisateur/relation";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }

    @GetMapping("/utilisateur/relation/abonnes")
    public String redirigerVersRelationAbonnes(HttpServletRequest request,
                                               Model model,
                                               @AuthenticationPrincipal String username,
                                               RedirectAttributes redirectAttributes) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
            Set<Utilisateur> follower = utilisateur.getFollowers();
            model.addAttribute("projetCount",projetService.findProjectsOfUser(utilisateur.getId()).size());
            model.addAttribute("type", "Abonnées");
            model.addAttribute("followers", follower);
            model.addAttribute("posts",postService.findAllPostOfFriends(utilisateur));
            return "utilisateur/relation";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }
    @PostMapping("/utilisateur/profil/changePfp")
    public String changeProfilPicture(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        HttpSession session = request.getSession(false);
        if (session!=null){
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            String finalName = utilisateur.getPseudo() +"."+originalFilename.split("\\.")[1];
            utilisateur.setPhotoUrl(finalName);
            File parentDir = new File(USER_FOLDER);
            File saveFile = new File(parentDir.getAbsolutePath() + File.separator + finalName);
            Files.copy(file.getInputStream(),saveFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
            file.getInputStream().close();
            utilisateurService.update(utilisateur);
            return "redirect:/utilisateur/profil/"+utilisateur.getPseudo();
        }
        return "auth";
    }
    @PostMapping("/utilisateur/profil/changeBanner")
    public String changerBanner(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        HttpSession session = request.getSession(false);
        if (session!=null){

            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

            if (utilisateur != null){
                if (file != null){
                    String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
                    FichierGeneral fichierGeneral = new FichierGeneral();
                    fichierGeneral.setUrlMedia(originalFilename);
                    if (postService.findBanniereUtilisateur(utilisateur) != null){
                        postService.deletePost(postService.findBanniereUtilisateur(utilisateur));
                    }
                    Post post = new Post();
                    post.setUtilisateur(utilisateur);
                    post.setTitre("titre");
                    post.setType("Banniere");
                    post.setTexte("banniere");
                    post.setPublique(false);
                    postService.savePost(file, utilisateur, originalFilename, fichierGeneral, post);
                }
            }

            return "redirect:/utilisateur/profil/"+utilisateur.getPseudo();
        }
        return "auth";
    }
    @GetMapping("/utilisateur/profil/settings")
    public String redirigerSettings(Model model,
                                    @AuthenticationPrincipal String username,
                                    HttpServletRequest request) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if(session != null){
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session, username);
            model.addAttribute("utilisateur", utilisateur);
            return "utilisateur/settings";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }

    @PostMapping("/utilisateur/profil/settings/update")
    public String updateUser(Utilisateur utilisateur, HttpServletRequest request) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session!=null){
            System.out.println(utilisateur.toString());
            Utilisateur user = (Utilisateur) session.getAttribute("user");
            user.setPseudo(utilisateur.getPseudo());
            user.setNom(utilisateur.getNom());
            user.setPrenom(utilisateur.getPrenom());
            if (!utilisateur.getPassword().isEmpty()){
                System.out.println("password changé à : " + utilisateur.getPassword());
                user.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            }
            utilisateurService.update(user);
            session.setAttribute("user",user);
            return "redirect:/utilisateur/profil/settings";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }
    @GetMapping("/utilisateur/profil/settings/delete")
    public String deleteUser(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if (session!=null){
            Utilisateur user = (Utilisateur) session.getAttribute("user");
            List<Post> posts = postService.findPostByUser(user);
            for (Post post: posts){
                postService.deletePost(post);
            }
            System.out.println("POSTS SUPPRIMÉS");
            List<Conversation> conversations = conversationService.findByAllByUtilisateur(user);
            for (Conversation conversation: conversations){
                if (conversation.getProjet()==null){
                    chatService.deleteAllByConversationId(conversation.getId());
                    conversationService.deleteById(conversation.getId());
                }
            }
            chatService.deleteAllByUtilisateurUnId(user.getId());
            System.out.println("CONVERSATION ET CHATS SUPPRIMÉES");
            List<Projet> projets = projetService.findProjectsOfUser(user.getId());
            for (Projet projet:projets){
                if (projet.getAdmin().getId().equals(user.getId())){
                    List<Utilisateur> users = projetService.getMembers(projet.getId());
                    for (Utilisateur utilisateur : users) {
                        projetService.removeUtilisateurFromProjet(projet.getId(), user.getId());
                    }
                    fichierService.deleteAllByProjet(projetService.findById(projet.getId()));
                    annonceService.deleteAllByProjetId(projet.getId());
                    tacheService.deleteAllByProjetId(projet.getId());
                    demandeService.deleteAllByProjetId(projet.getId());
                    chatService.deleteAllByConversationId(conversationService.findByProjet(projetService.findById(projet.getId())).getId());
                    conversationService.deleteAllByProjetId(projet.getId());
                    projetService.deleteProjet(projet.getId());
                }
                else {
                    projetService.removeUtilisateurFromProjet(projet.getId(),user.getId());
                }
            }
            System.out.println("PROJETS SUPPRIMÉS");
            Set<Utilisateur> followers = user.getFollowers();
            for (Utilisateur follower: followers){
                utilisateurService.updateRelations(user.getId(),follower.getId());
            }
            System.out.println("FOLLOWERS RETIRÉS");
            Set<Utilisateur> followings = user.getFollowing();
            for (Utilisateur following: followings){
                utilisateurService.updateRelations(following.getId(),user.getId());
            }
            System.out.println("FOLLOWINGS RETIRÉS");
            if (portfolioService.findByUtilisateur(user)!=null){
                portfolioService.deletePortfolio(portfolioService.findByUtilisateur(user));
            }
            System.out.println("PORTFOLIO SUPPRIMÉ");
            List<Forum> forums = forumService.findAllByUtilisateur(user);
            for (Forum forum:forums){
                List<Commentaire> commentaires = commentaireService.findAllByForum(forum);
                for (Commentaire commentaire:commentaires){
                    commentaireService.deleteCommentaire(commentaire);
                }
                forumService.deleteForum(forum);
            }
            System.out.println("FORUM SUPPRIMÉS");
            List<Commentaire> commentaires = commentaireService.findAllByUser(user);
            for (Commentaire commentaire:commentaires){
                commentaireService.deleteCommentaire(commentaire);
            }
            System.out.println("COMMENTAIRES SUPPRIMÉS");
            utilisateurService.delete(user.getId());
            System.out.println("UTILISATEUR SUPPRIMÉ");
            Cookie cookie = new Cookie("jwt",null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            session.invalidate();
        }
        return "redirect:/authentification";
    }

}
