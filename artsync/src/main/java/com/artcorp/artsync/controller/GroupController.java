package com.artcorp.artsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroupController {
    @GetMapping("/group")
    public String redirigerVersGroup() {
        return "groupe/group";
    }
}
