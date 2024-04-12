package com.artcorp.artsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForumController {
    @GetMapping("/forum")
    public String redirigerVersForum() {
        return "forum/forum";
    }
}
