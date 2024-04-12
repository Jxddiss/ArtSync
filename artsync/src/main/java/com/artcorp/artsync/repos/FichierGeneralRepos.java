package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.FichierGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichierGeneralRepos extends JpaRepository<FichierGeneral, Long> {

}
