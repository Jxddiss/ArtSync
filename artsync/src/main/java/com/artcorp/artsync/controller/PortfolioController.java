package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Portfolio;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.artcorp.artsync.repos.PortfolioRepos;
import com.artcorp.artsync.repos.ProjetRepos;
import com.artcorp.artsync.service.PostService;
import com.artcorp.artsync.service.ProjetService;
import com.artcorp.artsync.service.UtilisateurService;
import com.artcorp.artsync.service.impl.PortfolioServiceImpl;
import com.artcorp.artsync.service.impl.PostServiceImpl;
import com.artcorp.artsync.service.impl.ProjetServiceImpl;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/utilisateur")
public class PortfolioController {
    PortfolioServiceImpl portfolioService;
    PostService postService;
    UtilisateurService utilisateurService;
    @Autowired
    public PortfolioController(PortfolioServiceImpl portfolioService,
                               PostService postService,
                               UtilisateurServiceImpl utilisateurService) {
        this.portfolioService = portfolioService;
        this.postService = postService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/portfolio")
    public String redirigerVersPortfolio(Model model,
                                         HttpServletRequest request,
                                         @AuthenticationPrincipal String username,
                                         RedirectAttributes redirectAttributes) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
            if (utilisateur != null) {
                List<Post> listPosts = postService.findPostByUser(utilisateur);
                model.addAttribute("utilisateur", utilisateur);
                model.addAttribute("listPosts", listPosts);
                return "utilisateur/portfolio";
            }
            redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
            return "redirect:/authentification";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }
    @GetMapping("/ajouter-portfolio")
    public String ajoutPortfolio(HttpServletRequest request,
                                 @AuthenticationPrincipal String username,
                                 @RequestParam("code") String code,
                                 RedirectAttributes redirectAttributes) throws IOException, NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
            if (utilisateur != null){
                Portfolio portfolio = new Portfolio();
                portfolio.setCode(code);
                portfolio.setUtilisateur(utilisateur);
                portfolioService.createPortfolio(portfolio);
            }
            return "redirect:/utilisateur/profil/" + utilisateur.getPseudo();
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }
}
