package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.ConfirmationToken;
import com.artcorp.artsync.entity.UserPrincipal;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.exception.domain.MauvaisIdentifiantException;
import com.artcorp.artsync.service.impl.ConfirmationTokenServiceImpl;
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
import org.springframework.ui.Model;
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
    private ConfirmationTokenServiceImpl confirmationTokenService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UtilisateurServiceImpl utilisateurService, ConfirmationTokenServiceImpl confirmationTokenService, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.utilisateurService = utilisateurService;
        this.confirmationTokenService = confirmationTokenService;
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
        redirectAttributes.addFlashAttribute("error", "Inscription echouée");
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

    @GetMapping("/password-reset")
    public String passwordReset(){
        return "reset-password";
    }

    @PostMapping("/password-reset")
    public String genLinkPasswordReset(@RequestParam("email") String pseudo,
                                       RedirectAttributes redirectAttributes){
        String link = utilisateurService.genLinkPasswordReset(pseudo);
        if (link != null){
            System.out.println(link);
            redirectAttributes.addFlashAttribute("success","Lien envoyé");
            return "redirect:/authentification";
        }
        redirectAttributes.addFlashAttribute("error","Lien non envoyé");
        return "redirect:/authentification";
    }

    @GetMapping("/change-password")
    public String changePassword(@RequestParam("token") String token,
                                 Model model,
                                 RedirectAttributes redirectAttributes){
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token,new Date());
        if(token != null){
            model.addAttribute("userId",confirmationToken.getUserId());
            model.addAttribute("token",confirmationToken.getToken());
            return "change-password";
        }
        redirectAttributes.addFlashAttribute("error","Lien non valide");
        return "redirect:/authentification";
    }

    @PostMapping("/change-password")
    public String changePasswordForm(@RequestParam("mdp") String mdp,
                                     @RequestParam("userId") Long userId,
                                     @RequestParam("token") String token,
                                     RedirectAttributes redirectAttributes){
        if(utilisateurService.changePasswordFromToken(mdp,userId,token)){
            redirectAttributes.addFlashAttribute("success","Mot de passe mis à jour");
            return "redirect:/authentification";
        }
        redirectAttributes.addFlashAttribute("error","Mot de passe non mis à jour");
        return "redirect:/authentification";
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

    public ConfirmationTokenServiceImpl getConfirmationTokenService() {
        return confirmationTokenService;
    }

    public void setConfirmationTokenService(ConfirmationTokenServiceImpl confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }
}
