package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.UtilisateurService;
import com.artcorp.artsync.service.ProjetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;

@Controller
public class AppController {
    @Autowired
    UtilisateurService userService;
    @Autowired
    ProjetService projetService;

    @GetMapping("/index")
    public String redirigerVersIndex() {
        return "index";
    }
    @GetMapping("/explorer")
    public String redirigerVersExplorer() {
        return "explorer";
    }
    @GetMapping("/idee")
    public String redirigerVersIdee() {
        return "boite-idee";
    }
    @GetMapping("/classement")
    public String redirigerVersClassement() {
        return "classement";
    }
    @GetMapping("/recherche")
    public String redirigerVersRecherche(@RequestParam(value = "filtre", required = false) String filtre,
                                         @RequestParam(value = "type", required = false) String type,
                                         @RequestParam(value = "search", required = false) String search,
                                         Model model,
                                         HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = null;
        Long idUtilisateur = null;
        try {
            utilisateur = ((Utilisateur) session.getAttribute("user"));
            idUtilisateur = utilisateur.getId();
        } catch (Exception e) {
            //do nothing
        }


        if ("search".equals(type)) {
            if ("UTILISATEUR".equals(filtre)) {
                List<Utilisateur> listUtilisateurs = userService.findByKeyword(search);
                for (Utilisateur user : listUtilisateurs) {
                    if (utilisateur!=null && (user.getFollowers().contains(utilisateur) || user.getAmis().contains(utilisateur))) {
                        user.setIn(true);
                    }
                }
                if (utilisateur!=null && listUtilisateurs.contains(utilisateur)) {
                    listUtilisateurs.remove(utilisateur);
                }
                model.addAttribute("listUtilisateurs", listUtilisateurs);
                if (listUtilisateurs.size()<1){
                    model.addAttribute("message", "Aucun utilisateur trouvé avec le filtre '"+search+"'");
                }
            } else if ("GROUPE".equals(filtre)) {
                List<Projet> listProjets = projetService.findByKeyword(search);
                for (Projet projet : listProjets) {
                    if (utilisateur!=null && projet.getUtilisateurs().contains(userService.findById(idUtilisateur))) {
                        projet.setIn(true);
                    } else {
                        projet.setIn(false);
                    }
                }
                model.addAttribute("listProjets", listProjets);
                if (listProjets.size()<1){
                    model.addAttribute("message", "Aucun projet trouvé avec le filtre '"+search+"'");
                }
            } else {
                //ajoute stream ici quand on sera rendu la
            }
        } else {
            if ("UTILISATEUR".equals(filtre)) {
                List<Utilisateur> listUtilisateurs = userService.findAll();
                for (Utilisateur user : listUtilisateurs) {
                    if (utilisateur!=null && (user.getFollowers().contains(utilisateur) || user.getAmis().contains(utilisateur))) {
                        user.setIn(true);
                    }
                }
                if (utilisateur!=null && listUtilisateurs.contains(utilisateur)) {
                    listUtilisateurs.remove(utilisateur);
                }
                model.addAttribute("listUtilisateurs", listUtilisateurs);
                if (listUtilisateurs.size()<1){
                    model.addAttribute("message", "Aucun utilisateur trouvé");
                }

            } else if ("GROUPE".equals(filtre)) {
                List<Projet> listProjets = projetService.findAll();
                for (Projet projet : listProjets) {
                    if (utilisateur!=null && projet.getUtilisateurs().contains(userService.findById(idUtilisateur))) {
                        projet.setIn(true);
                    } else {
                        projet.setIn(false);
                    }
                }
                model.addAttribute("listProjets", listProjets);
                if (listProjets.size()<1){
                    model.addAttribute("message", "Aucun projet trouvé");
                }
            } else {
                //ajoute stream ici quand on sera rendu la
            }
        }
        model.addAttribute("filtre", filtre);
        model.addAttribute("type", type);
        return "recherche";
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
    @GetMapping("/recherche/follow")
    public String manageFollow(@RequestParam("id") Long id, @RequestParam("type") String type, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long idUtilisateur = null;
        try {
            Utilisateur utilisateur = ((Utilisateur) session.getAttribute("user"));
            idUtilisateur = utilisateur.getId();
        } catch (Exception e) {
            return "auth";
        }
        if ("follow".equals(type)) {
            userService.addFollower(id, idUtilisateur);
        } else if ("unfollow".equals(type)) {
            userService.removeFollower(id, idUtilisateur);
        }
        return "recherche";
    }
}
