package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Annonce;

import java.util.List;

public interface AnnonceService {
    //find all annonces of projects
    public List<Annonce> findByProjetId(Long id);
}
