package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.DemandeAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeAdminRepos extends JpaRepository<DemandeAdmin,Long> {
}
