package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.DemandeAdmin;
import com.artcorp.artsync.repos.DemandeAdminRepos;
import com.artcorp.artsync.service.DemandeAdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DemandeAdminServiceImpl implements DemandeAdminService {
    private final DemandeAdminRepos demandeAdminRepos;

    @Autowired
    public DemandeAdminServiceImpl(DemandeAdminRepos demandeAdminRepos) {
        this.demandeAdminRepos = demandeAdminRepos;
    }

    @Override
    public List<DemandeAdmin> findAll() {
        return demandeAdminRepos.findAll();
    }

    @Override
    public DemandeAdmin save(DemandeAdmin demandeAdmin) {
        if (demandeAdminRepos.existsByUserId(demandeAdmin.getUserId())){
            return null;
        }
        return demandeAdminRepos.save(demandeAdmin);
    }

    @Override
    public void delete(DemandeAdmin demandeAdmin) {
        demandeAdminRepos.delete(demandeAdmin);
    }
}
