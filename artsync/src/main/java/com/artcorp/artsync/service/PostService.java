package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;

import java.util.List;

public interface PostService {

    List<Post> findPostsByYear( int year);
    List<Post> findPostsByMonth(int month, int year);
    List<Post> findPostsByDay( int day,  int month,  int year);
    List<Post> findPostFollowing( List<Utilisateur> followingList);
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

}
