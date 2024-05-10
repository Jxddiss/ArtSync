package com.artcorp.artsync.handlers;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        HttpSession session = request.getSession(false);
        Utilisateur utilisateur;

        if (session != null){
            utilisateur = (Utilisateur) session.getAttribute("user");
        }else{
            String username = authentication.getName();
            utilisateur = utilisateurService.findByPseudo(username);
        }

        if (utilisateur != null) {
            utilisateur.setStatut("offline");
            utilisateurService.update(utilisateur);
        }
    }
}
