package com.artcorp.artsync.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Arrays;

import static com.artcorp.artsync.constant.FileConstant.PLAYABLE_EXTENSIONS;

@Entity
public class FichierGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlMedia;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "forum_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Forum forum;
    @ManyToOne
    @JoinColumn(name = "projet_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Projet projet;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    private String type;

    public FichierGeneral() {
    }

    public FichierGeneral(Long id, String urlMedia, String type) {
        this.id = id;
        this.urlMedia = urlMedia;
        this.type = type;
    }

    public Post getPost() {
        return post;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (Arrays.asList(PLAYABLE_EXTENSIONS).contains(type)){
            this.type = "video";
        }else{
            this.type = type;
        }
    }
}
