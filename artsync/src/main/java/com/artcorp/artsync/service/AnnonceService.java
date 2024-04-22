package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Annonce;

import java.util.List;

public interface AnnonceService {
    public List<Annonce> findByProjetId(Long id);
    public Annonce createAnnonce(Annonce annonce);
    public void deleteAllByProjetId(Long id);

}
