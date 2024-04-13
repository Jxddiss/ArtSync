package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.ProjetRepos;
import com.artcorp.artsync.service.ProjetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProjetServiceImpl implements ProjetService {
    private ProjetRepos repos;
    @Autowired
    public ProjetServiceImpl(ProjetRepos repos) {
        this.repos = repos;
    }

    //find all projects
    @Override
    public List<Projet> findAll() {
        return repos.findAll();
    }
    @Override
    public List<Projet> findByKeyword(String keyword) {
        return repos.findByKeyword(keyword);
    }

}
