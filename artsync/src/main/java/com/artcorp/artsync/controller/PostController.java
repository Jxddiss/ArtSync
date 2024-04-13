package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.service.PostService;
import com.artcorp.artsync.service.impl.PostServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping("/explorer")
    public String explorer(HttpServletRequest request,
                           Model model) {
        HttpSession session = request.getSession(false);
        List<Post> postsEnVedette = null;
        List<Post> posts = null;
        if (session == null) {
            postsEnVedette = postService.findByPubliqueEnVedette(true);
            posts = postService.findByPubliqueEnVedette(true);
        }else{
            postsEnVedette = postService.findAllPostsEnVedette();
            posts = postService.findAllPosts();
        }
        model.addAttribute("postsEnVedette", postsEnVedette);
        model.addAttribute("posts", posts);
        return "explorer";
    }
}
