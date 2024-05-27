package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestController
public class ForumAdminRestController {
    private final ForumService forumService;

    @Autowired
    public ForumAdminRestController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/api/forums")
    public ResponseEntity<List<Forum>> getAllForumAdmin(){
        List<Forum> listeForum = forumService.findAll();
        return new ResponseEntity<>(listeForum, HttpStatus.OK);
    }

    @GetMapping("/api/forums/{forumId}")
    public ResponseEntity<Forum> getOneForumAdmin(@PathVariable("forumId") Long forumId,
                                                  HttpMethod method) throws NoResourceFoundException {

        Forum forum = forumService.findById(forumId);
        if (forum == null) {
            throw new NoResourceFoundException(method,"Forum id: "+ forumId);
        }
        return new ResponseEntity<>(forum, HttpStatus.OK);
    }
}
