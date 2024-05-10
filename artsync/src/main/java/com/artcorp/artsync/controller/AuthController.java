package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.UserPrincipal;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.exception.domain.MauvaisIdentifiantException;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import com.artcorp.artsync.utils.JWTTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

import static com.artcorp.artsync.constant.FileConstant.DEFAULT_USER_IMAGE;
import static com.artcorp.artsync.constant.SecurityConstant.EXPIRATION_TIME;
import static com.artcorp.artsync.constant.SecurityConstant.JWT_TOKEN_HEADER;

@Controller
public class AuthController {
    private UtilisateurServiceImpl utilisateurService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UtilisateurServiceImpl utilisateurService, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.utilisateurService = utilisateurService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/authentification")
    public String connexion(@RequestParam("username") String username,
                            @RequestParam("mdp") String mdp,
                            HttpServletRequest request,
                            HttpServletResponse response) throws MauvaisIdentifiantException {
        authenticate(username,mdp);
        Utilisateur utilisateur = utilisateurService.findByPseudo(username);
        UserPrincipal userPrincipal = new UserPrincipal(utilisateur);
        Cookie jwtCookie = new Cookie("jwt",getJwtCookie(userPrincipal));
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge((int)(new Date(System.currentTimeMillis() + EXPIRATION_TIME).getTime()/1000));
        response.addCookie(jwtCookie);
        if (utilisateur != null) {
            utilisateur.setStatut("online");
            utilisateur = utilisateurService.update(utilisateur);

            HttpSession session = request.getSession();
            session.setAttribute("user", utilisateur);
            return "redirect:/feed";
        }else{
            throw new MauvaisIdentifiantException("Mauvais identifiants");
        }
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
            redirectAttributes.addFlashAttribute("success", "Inscription reussie");
            return "redirect:/authentification";
        }
        redirectAttributes.addFlashAttribute("error", "Inscription echoue");
        return "redirect:/authentification";
    }

    @GetMapping("/authentification")
    public String authentification(HttpServletRequest request, @AuthenticationPrincipal String username) {
        HttpSession session = request.getSession();

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        if (utilisateur != null || !username.equalsIgnoreCase("anonymousUser")){
            return "redirect:/feed";
        }
        return "auth";
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private String getJwtCookie(UserPrincipal userPrincipal) {
        return jwtTokenProvider.generateJwtToken(userPrincipal);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
