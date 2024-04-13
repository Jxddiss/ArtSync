package com.artcorp.artsync.entity;

import jakarta.persistence.*;

@Entity
public class FichierGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlMedia;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    public FichierGeneral() {
    }

    public FichierGeneral(Long id, String urlMedia) {
        this.id = id;
        this.urlMedia = urlMedia;
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
}
