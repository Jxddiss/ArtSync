package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface PostService {

    List<Post> findPostsByYear( int year);
    List<Post> findPostsByMonth(int month, int year);
    List<Post> findPostsByDay( int day,  int month,  int year);
    List<Post> findPostFollowing( Set<Utilisateur> followingList);
    List<Post> findByPubliqueEnVedette(boolean publique);
    List<Post> findAllPostsPublique(boolean publique);
    List<Post> findAllPostsEnVedette();
    List<Post> findAllPosts();
    List<Post> findByTypeEnVedettePublique(String type, boolean publique);
    List<Post> findAllPostsByTypePublique(String type, boolean publique);
    List<Post> findByTypeEnVedette(String type);
    List<Post> findAllPostsByType(String type);
    void deletePost(Post post);
    Post addPost(Post post);

    List<Post> findPostByUser(Utilisateur utilisateur);

    Post findBanniereUtilisateur(Utilisateur utilisateur);

    Post findById(Long postId);

    void likePost(Long postId);
    void unLikePost(Long postId);
    List<Post> findTop10Posts();

    List<Post>findAllByUtilisateurAndPublique(Utilisateur utilisateur);
    List<Post> findAllPostOfFriends(Utilisateur utilisateur);
}
