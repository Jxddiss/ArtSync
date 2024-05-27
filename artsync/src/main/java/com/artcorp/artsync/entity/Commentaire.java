package com.artcorp.artsync.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String message;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Forum forum;

    @ManyToOne
    @JoinColumn(name="utilisateur_id")
    private Utilisateur utilisateur;

    public Commentaire() {
    }
    public Commentaire(Long id, String message, Post post, Forum forum, Utilisateur utilisateur) {
        this.id = id;
        this.message = message;
        this.post = post;
        this.forum = forum;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", post=" + post +
                ", forum=" + forum +
                ", utilisateur=" + utilisateur +
                '}';
    }
}
