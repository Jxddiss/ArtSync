package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.PostRepos;
import com.artcorp.artsync.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    public List<Post> findPostFollowing(Set<Utilisateur> followingList) {
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

    @Override
    public List<Post> findPostByUser(Utilisateur utilisateur) {
        return postRepos.findPostByUser(utilisateur);
    }

    @Override
    public Post findBanniereUtilisateur(Utilisateur utilisateur) {
        return postRepos.findBanniereUtilisateur(utilisateur);
    }

    @Override
    public Post findById(Long postId) {
        return postRepos.findById(postId).get();
    }

    @Override
    public void likePost(Long postId) {
        Post post = postRepos.findById(postId).get();
        post.setNbLikes(post.getNbLikes() + 1);
        postRepos.save(post);
    }

    @Override
    public void unLikePost(Long postId) {
        Post post = postRepos.findById(postId).get();
        post.setNbLikes(post.getNbLikes() - 1);
        postRepos.save(post);
    }

    @Override
    public List<Post> findTop10Posts() {
        return postRepos.findTop10Posts();
    }

    @Override
    public List<Post> findAllByUtilisateurAndPublique(Utilisateur utilisateur) {
        return postRepos.findAllByUtilisateurAndPublique(utilisateur,true);
    }


}
