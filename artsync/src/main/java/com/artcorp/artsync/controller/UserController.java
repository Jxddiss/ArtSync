package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;

@Controller
public class UserController {
    private UtilisateurServiceImpl utilisateurService;

    @Autowired
    public UserController(UtilisateurServiceImpl utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/conversation")
    public String redirigerVersConversation() {
        return "utilisateur/conversation";
    }
    @GetMapping("/feed")
    public String redirigerVersFeed() {
        return "utilisateur/feed";
    }
    @GetMapping("utilisateur/profil/{pseudo}")
    public String redirigerVersProfil(@PathVariable("pseudo") String pseudo,
                                      Model model,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = utilisateurService.findByPseudo(pseudo);
            if (utilisateur != null) {
                model.addAttribute("utilisateur", utilisateur);
                return "utilisateur/profile";
            }
        }
        redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
        return "redirect:/authentification";
    }
    @GetMapping("/relation")
    public String redirigerVersRelation() {
        return "utilisateur/relation";
    }
    @GetMapping("/portfolio")
    public String redirigerVersPortfolio() {
        return "utilisateur/portfolio";
    }
    @GetMapping("/media/images/{image}")
    public void getImage(@PathVariable("image") String fileName, HttpServletResponse response) throws IOException {
        File dir = new File(USER_FOLDER);
        File file = new File(dir.getAbsolutePath() + File.separator + fileName);

        if (file.exists()) {
            response.setContentType("image/jpeg");

            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
            out.flush();
            out.close();
        }
    }
}
