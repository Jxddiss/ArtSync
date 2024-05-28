package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.DemandeAdmin;
import com.artcorp.artsync.repos.DemandeAdminRepos;
import com.artcorp.artsync.service.DemandeAdminService;

import java.util.List;

public class DemandeAdminServiceImpl implements DemandeAdminService {
    private final DemandeAdminRepos demandeAdminRepos;

    public DemandeAdminServiceImpl(DemandeAdminRepos demandeAdminRepos) {
        this.demandeAdminRepos = demandeAdminRepos;
    }

    @Override
    public List<DemandeAdmin> findAll() {
        return demandeAdminRepos.findAll();
    }

    @Override
    public DemandeAdmin save(DemandeAdmin demandeAdmin) {
        return demandeAdminRepos.save(demandeAdmin);
    }

    @Override
    public void delete(DemandeAdmin demandeAdmin) {
        demandeAdminRepos.delete(demandeAdmin);
    }
}
