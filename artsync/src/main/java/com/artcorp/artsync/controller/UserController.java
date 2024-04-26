package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.repos.ProjetRepos;
import com.artcorp.artsync.service.impl.PortfolioServiceImpl;
import com.artcorp.artsync.service.impl.PostServiceImpl;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;

@Controller
@RequestMapping("/utilisateur")
public class UserController {
    private UtilisateurServiceImpl utilisateurService;
    private ProjetRepos projetRepos;
    private PostServiceImpl postService;
    private PortfolioServiceImpl portfolioService;

    @Autowired
    public UserController(UtilisateurServiceImpl utilisateurService, ProjetRepos projetRepos, PostServiceImpl postService, PortfolioServiceImpl portfolioService) {
        this.utilisateurService = utilisateurService;
        this.projetRepos = projetRepos;
        this.postService = postService;
        this.portfolioService = portfolioService;
    }

    @GetMapping("/feed")
    public String redirigerVersFeed() {
        return "utilisateur/feed";
    }
    @GetMapping("/profil/{pseudo}")
    public String redirigerVersProfil(@PathVariable("pseudo") String pseudo,
                                      Model model,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null && pseudo != null && (Utilisateur) session.getAttribute("user") != null) {
            Utilisateur utilisateur = utilisateurService.findByPseudo(pseudo);
            if (utilisateur != null) {
                List<Post> listPosts = postService.findPostByUser(utilisateur);
                Post banniere = postService.findBanniereUtilisateur(utilisateur);
                int nbAbonnes = utilisateur.getFollowers().size();
                int nbAbonnements = utilisateur.getFollowing().size();
                Portfolio portfolio = portfolioService.findByUtilisateur(utilisateur);
                model.addAttribute("utilisateur", utilisateur);
                model.addAttribute("listPosts", listPosts);
                model.addAttribute("banniere", banniere);
                model.addAttribute("nbAbonnes", nbAbonnes);
                model.addAttribute("nbAbonnements", nbAbonnements);
                model.addAttribute("portfolio", portfolio);
                return "utilisateur/profil";
            }
            redirectAttributes.addFlashAttribute("error", "Cet utilisateur n'existe pas");
            return "redirect:/utilisateur/feed";
        }
        redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
        return "redirect:/authentification";
    }
    @GetMapping("/relation")
    public String redirigerVersRelation(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            if (utilisateur != null) {
                Set<Utilisateur> following = utilisateur.getFollowing();
                model.addAttribute("type", "Abonnements");
                model.addAttribute("following", following);
                return "utilisateur/relation";
            }
            redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
            return "redirect:/authentification";
        }
        redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
        return "redirect:/authentification";
    }

    @GetMapping("/relation/groupes")
    public String redirigerVersRelationGroupes(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            if (utilisateur != null) {
                List<Projet> groupes = projetRepos.findByUtilisateursId(utilisateur.getId());
                model.addAttribute("type", "Groupes");
                model.addAttribute("projets", groupes);
                return "utilisateur/relation";
            }
            redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
            return "redirect:/authentification";
        }
        redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
        return "redirect:/authentification";
    }

    @GetMapping("/relation/abonnes")
    public String redirigerVersRelationAbonnes(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            if (utilisateur != null) {
                Set<Utilisateur> follower = utilisateur.getFollowers();
                model.addAttribute("type", "Abonnées");
                model.addAttribute("followers", follower);
                return "utilisateur/relation";
            }
            redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
            return "redirect:/authentification";
        }
        redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
        return "redirect:/authentification";
    }
    @PostMapping("/profil/changePfp")
    public String changeProfilPicture(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        HttpSession session = request.getSession(false);
        if (session!=null){
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            utilisateur.setPhotoUrl(originalFilename);
            File parentDir = new File(USER_FOLDER);
            File saveFile = new File(parentDir.getAbsolutePath() + File.separator + originalFilename);
            file.transferTo(saveFile);
            utilisateurService.update(utilisateur);
            return "redirect:/utilisateur/profil/"+utilisateur.getPseudo()+"";
        }
        return "auth";
    }
    @PostMapping("/profil/changeBanner")
    public String changerBanner(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        HttpSession session = request.getSession(false);
        if (session!=null){

            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

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
            post.setDate(LocalDateTime.now());
            post.setPseudoUtilisateur(utilisateur.getPseudo());

            fichierGeneral.setPost(post);

            HashSet<FichierGeneral> listFichiers = new HashSet<>();
            listFichiers.add(fichierGeneral);
            post.setListeFichiers(listFichiers);

            File parentDir = new File(USER_FOLDER);
            File saveFile = new File(parentDir.getAbsolutePath() + File.separator + originalFilename);
            file.transferTo(saveFile);
            postService.addPost(post);

            return "redirect:/utilisateur/profil/"+utilisateur.getPseudo()+"";
        }
        return "auth";
    }
    @GetMapping("/profil/settings")
    public String redirigerSettings(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session!=null){
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            model.addAttribute("utilisateur",utilisateur);
            return "utilisateur/settings";
        }
        return "auth";
    }

}
