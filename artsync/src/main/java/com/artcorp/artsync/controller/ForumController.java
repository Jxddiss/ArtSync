package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;
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
import java.util.HashSet;
import java.util.List;

import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;

@Controller
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping("/forum")
    public String redirigerVersForum(Model model) {
        List<Forum> forums = forumService.findAllByPubliqueTrue();
        for (Forum forum: forums){
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

    public ArrayList<String> getTags(String string){
        ArrayList<String> tags = new ArrayList<>();
        String[] tagArray = string.split(",");
        for (String tag : tagArray) {
            tags.add(tag.trim().toLowerCase());
        }
        return tags;
    }
}
