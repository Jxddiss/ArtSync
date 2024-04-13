package com.artcorp.artsync.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class UserController {
    public static final String USER_FOLDER = "src\\main\\resources\\static\\media\\image\\";
    @GetMapping("/conversation")
    public String redirigerVersConversation() {
        return "utilisateur/conversation";
    }
    @GetMapping("/feed")
    public String redirigerVersFeed() {
        return "utilisateur/feed";
    }
    @GetMapping("/profil")
    public String redirigerVersProfil() {
        return "utilisateur/profile";
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
    public void getProfileImage(@PathVariable("image") String fileName, HttpServletResponse response) throws IOException {
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
