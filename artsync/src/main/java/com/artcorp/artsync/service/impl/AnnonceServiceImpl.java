package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Annonce;
import com.artcorp.artsync.repos.AnnonceRepos;
import com.artcorp.artsync.repos.PortfolioRepos;
import com.artcorp.artsync.repos.ProjetRepos;
import com.artcorp.artsync.service.AnnonceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class AnnonceServiceImpl implements AnnonceService {
    private AnnonceRepos repos;
    @Autowired
    public AnnonceServiceImpl(AnnonceRepos repos) {
        this.repos = repos;
    }
    @Override
    public List<Annonce> findByProjetId(Long id) {
        return repos.findByProjetId(id);

    }
    @Override
    public Annonce createAnnonce(Annonce annonce) {
        return repos.save(annonce);
    }

}
