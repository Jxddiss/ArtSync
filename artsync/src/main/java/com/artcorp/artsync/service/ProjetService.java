package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Projet;

import java.util.List;

public interface ProjetService {

    List<Projet> findAll();
    List<Projet> findByKeyword(String keyword);
}
