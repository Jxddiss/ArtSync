package com.artcorp.artsync.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String titre;
    private String texte;
    private boolean publique;
    @OneToMany(mappedBy = "post")
    private Set<FichierGeneral> listeFichiers;
    private int nbLikes;
    private int nbPartages;
    private int nbVues;
    private String type;
    private String pseudoUtilisateur;
    private String profilUrl;
    @OneToMany(mappedBy = "post")
    private Set<Commentaire> listeCommentaires;
}
