package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DemandeRepos extends JpaRepository<Demande, Long> {
    public List<Demande> findByStatus(String status);
    public List<Demande> findByUtilisateurId(Long id);

}
