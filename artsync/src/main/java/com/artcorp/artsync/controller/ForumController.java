package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping("/forum")
    public String redirigerVersForum() {
        return "forum/forum";
    }

    @PostMapping("/forum/create")
    @ResponseBody
    public Forum createForum(@RequestBody Forum forum) {
        return forumService.createForum(forum);
    }
}
