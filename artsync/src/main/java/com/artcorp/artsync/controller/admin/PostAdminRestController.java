package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.service.PostService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestController
public class PostAdminRestController {
    private final PostService postService;

    public PostAdminRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> getAllPostAdmin(){
        List<Post> listPost = postService.findAllPosts();
        return new ResponseEntity<>(listPost, HttpStatus.OK);
    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<Post> getAllPostAdmin(@PathVariable("postId") Long postId,
                                                HttpMethod method) throws NoResourceFoundException {
        Post post = postService.findById(postId);
        if (post == null){
            throw new NoResourceFoundException(method,"Post id: "+ postId);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
