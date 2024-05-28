package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.DemandeAdmin;

import java.util.List;

public interface DemandeAdminService {
    List<DemandeAdmin> findAll();
    DemandeAdmin save(DemandeAdmin demandeAdmin);
    void delete(DemandeAdmin demandeAdmin);
}
