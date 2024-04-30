package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.service.CommentaireService;
import com.artcorp.artsync.service.ForumService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;

@Controller
public class ForumController {

    @Autowired
    private ForumService forumService;
    @Autowired
    private CommentaireService commentaireService;

    @GetMapping("/forum")
    public String redirigerVersForum(Model model) {
        List<Forum> forums = forumService.findAllByPubliqueTrue();
        if (forums.size()<1){
            model.addAttribute("message","Il n'y a aucun thread.");
        }
        for (Forum forum: forums){
            forum.setListeCommentaires(commentaireService.findAllByForum(forum));
            if (forum.getFiltres()!=null){
                forum.setTags(getTags(forum.getFiltres()));
            }
        }
        model.addAttribute("forum",new Forum());
        model.addAttribute("threads",forums);
        return "forum/forum";
    }
    @GetMapping("/forum/user")
    public String getYourThreads(Model model ,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        List<Forum> forums = forumService.findAllByUtilisateur(utilisateur);
        if (forums.size()<1){
            model.addAttribute("message","Vous n'avez aucun thread.");
        }
        for (Forum forum: forums){
            if (forum.getFiltres()!=null){
                forum.setTags(getTags(forum.getFiltres()));
            }
        }
        model.addAttribute("forum",new Forum());
        model.addAttribute("threads",forums);
        return "forum/forum";
    }

    @GetMapping("/forum/following")
    public String getFollowingThreads(Model model ,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        List<Forum> forums = new ArrayList<Forum>();
        for (Utilisateur following: utilisateur.getFollowing()){
            List<Forum> forumFollowing = forumService.findAllByUtilisateurAndPublique(following);
            forums.addAll(forumFollowing);
        }
        if (forums.size()<1){
            model.addAttribute("message","Vous n'avez aucun thread de vos abonnements.");
        }
        for (Forum forum: forums){
            forum.setListeCommentaires(commentaireService.findAllByForum(forum));
            if (forum.getFiltres()!=null){
                forum.setTags(getTags(forum.getFiltres()));
            }
        }
        model.addAttribute("forum",new Forum());
        model.addAttribute("threads",forums);
        return "forum/forum";
    }
    @PostMapping("/forum/create")
    public String createForum(Forum forum,
                             HttpServletRequest request,
                             @RequestParam("contenu") String contenu,
                             @RequestParam(value = "tag", required = false) String[] selectedTags,
                             @RequestParam(value="fichier", required = false) MultipartFile fichier) throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        forum.setContenu(contenu);
        forum.setDateCreation(LocalDateTime.now());
        forum.setUtilisateur(utilisateur);
        if (selectedTags != null && selectedTags.length > 0) {
            String tags = "";
            for (String tag : selectedTags) {
                tags+=tag+",";
            }
           forum.setFiltres(tags);
        }
        forumService.createForum(forum);
        return "redirect:/forum";
    }
    @PostMapping("/forum/search")
    public String searchForums(@RequestParam(value = "keyword") String keyword, Model model) {
        List<Forum> forums = forumService.searchForumsByTitle(keyword);
        for (Forum forum: forums){
            forum.setListeCommentaires(commentaireService.findAllByForum(forum));
            if (forum.getFiltres()!=null){
                forum.setTags(getTags(forum.getFiltres()));
            }
        }
        model.addAttribute("forum",new Forum());
        model.addAttribute("threads", forums);
        return "forum/forum";
    }

    public ArrayList<String> getTags(String string){
        ArrayList<String> tags = new ArrayList<>();
        String[] tagArray = string.split(",");
        for (String tag : tagArray) {
            tags.add(tag.trim().toLowerCase());
        }
        return tags;
    }


}
