package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.artcorp.artsync.constant.FileConstant.DEFAULT_USER_IMAGE;

@Controller
public class AuthController {
    private UtilisateurServiceImpl utilisateurService;

    @Autowired
    public AuthController(UtilisateurServiceImpl utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/authentification")
    public String connexion(@RequestParam("username") String username,
                            @RequestParam("mdp") String mdp,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = utilisateurService.connexion(username, mdp);

        if (utilisateur != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", utilisateur);
            return "redirect:/feed";
        }
        redirectAttributes.addFlashAttribute("error", "Connexion echoué");
        return "redirect:/authentification";
    }

    @PostMapping("/inscription")
    public String inscription(@RequestParam("username") String username,
                              @RequestParam("mdp") String mdp,
                              @RequestParam("email") String email,
                              @RequestParam("nom") String nom,
                              @RequestParam("prenom") String prenom,
                              @RequestParam("specialite") String specialite,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {

        Utilisateur utilisateur = utilisateurService
                .inscription(username,prenom,nom,email,mdp,DEFAULT_USER_IMAGE,specialite,"actif");
        if (utilisateur != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", utilisateur);
            redirectAttributes.addFlashAttribute("success", "Inscription reussie");
            return "redirect:/utilisateur/profil/" + utilisateur.getPseudo();
        }
        redirectAttributes.addFlashAttribute("error", "Inscription echoue");
        return "redirect:/authentification";
    }

    @GetMapping("/deconnexion")
    public String deconnexion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/authentification")
    public String authentification() {
        return "auth";
    }

}
