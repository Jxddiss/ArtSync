package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.LiveStream;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.LiveStreamService;
import com.artcorp.artsync.service.PostService;
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
    @Autowired
    LiveStreamService liveStreamService;
    @Autowired
    PostService postService;

    @GetMapping("/index")
    public String redirigerVersIndex() {
        return "index";
    }

    @GetMapping("/idee")
    public String redirigerVersIdee() {
        return "boite-idee";
    }
    @GetMapping("/classement")
    public String redirigerVersClassement(Model model) {
        List<Post> posts = postService.findTop10Posts();
        System.out.println("---------------------------"+posts.size());
        model.addAttribute("posts",posts);
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
                    if (utilisateur!=null && (user.getFollowing().contains(utilisateur))) {
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
                List<LiveStream> streams = liveStreamService.findAllByKeyword(search);
                for (LiveStream stream: streams){
                    stream.setUtilisateur(userService.findByPseudo(stream.getPseudoStreamer()));
                }
                model.addAttribute("listStreams", streams);
                if (streams.size()<1){
                    model.addAttribute("message", "Aucun stream trouvé");
                }
            }
        } else {
            if ("UTILISATEUR".equals(filtre)) {
                List<Utilisateur> listUtilisateurs = userService.findAll();
                for (Utilisateur user : listUtilisateurs) {
                    if (utilisateur!=null){
                        for (Utilisateur follower: user.getFollowers()){
                            if (follower.getPseudo().equals(utilisateur.getPseudo())){
                                user.setIn(true);
                                break;
                            }
                        }
                    }
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
                List<LiveStream> streams = liveStreamService.getAllActiveLiveStream();
                for (LiveStream stream: streams){
                    stream.setUtilisateur(userService.findByPseudo(stream.getPseudoStreamer()));
                }
                model.addAttribute("listStreams", streams);
                if (streams.size()<1){
                    model.addAttribute("message", "Aucun stream trouvé");
                }
            }
        }
        model.addAttribute("filtre", filtre);
        model.addAttribute("type", type);
        return "recherche";
    }
    @GetMapping("/recherche/follow")
    public String manageFollow(@RequestParam("id") Long id, @RequestParam("type") String type, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Utilisateur utilisateur;
        Long idUtilisateur = null;
        try {
            utilisateur = ((Utilisateur) session.getAttribute("user"));
            idUtilisateur = utilisateur.getId();
        } catch (Exception e) {
            return "auth";
        }
        userService.updateRelations(id, idUtilisateur);
        utilisateur = userService.findById(idUtilisateur);
        session.setAttribute("user",utilisateur);
        return "redirect:/recherche";
    }
}
