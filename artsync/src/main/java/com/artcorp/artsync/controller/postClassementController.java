package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.repos.PostRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class postClassementController {

        @Autowired
        private PostRepos postRepository;

        @GetMapping("/posts/classement/year")
        public List<Post> classementByYear(@RequestParam int year) {
            return postRepository.findPostsByYear(year);
        }

        @GetMapping("/posts/classement/month")
        public List<Post> classementByMonth(@RequestParam int month, @RequestParam int year) {
            return postRepository.findPostsByMonth(month, year);
        }

        @GetMapping("/posts/classement/day")
        public List<Post> classementByDay(@RequestParam int day, @RequestParam int month, @RequestParam int year) {
            return postRepository.findPostsByDay(day, month, year);
        }



}
