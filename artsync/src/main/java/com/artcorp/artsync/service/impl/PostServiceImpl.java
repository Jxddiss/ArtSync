package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.PostRepos;
import com.artcorp.artsync.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepos postRepos;

    @Autowired
    public PostServiceImpl(PostRepos postRepos) {
        this.postRepos = postRepos;
    }

    @Override
    public List<Post> findPostsByYear(int year) {
        return postRepos.findPostsByYear(year);
    }

    @Override
    public List<Post> findPostsByMonth(int month, int year) {
        return postRepos.findPostsByMonth(month, year);
    }

    @Override
    public List<Post> findPostsByDay(int day, int month, int year) {
        return postRepos.findPostsByDay(day, month, year);
    }

    @Override
    public List<Post> findPostFollowing(List<Utilisateur> followingList) {
        return postRepos.findPostFollowing(followingList);
    }

    @Override
    public List<Post> findByPubliqueEnVedette(boolean publique) {
        return postRepos.findByPubliqueEnVedette(publique);
    }

    @Override
    public List<Post> findAllPostsPublique(boolean publique) {
        return postRepos.findAllPostsPublique(publique);
    }

    @Override
    public List<Post> findAllPostsEnVedette() {
        return postRepos.findPostsEnVedette();
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepos.findAll();
    }

    @Override
    public List<Post> findByTypeEnVedettePublique(String type, boolean publique) {
        return postRepos.findByTypeEnVedettePublique(type, publique);
    }

    @Override
    public List<Post> findAllPostsByTypePublique(String type, boolean publique) {
        return postRepos.findAllPostsByTypePublique(type, publique);
    }

    @Override
    public List<Post> findByTypeEnVedette(String type) {
        return postRepos.findByTypeEnVedette(type);
    }

    @Override
    public List<Post> findAllPostsByType(String type) {
        return postRepos.findAllPostsByType(type);
    }

    @Override
    public void deletePost(Post post) {
        postRepos.delete(post);
    }

    @Override
    public Post addPost(Post post) {
        return postRepos.save(post);
    }
}
