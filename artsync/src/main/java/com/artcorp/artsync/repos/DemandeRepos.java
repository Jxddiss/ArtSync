package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DemandeRepos extends JpaRepository<Demande, Long> {
    List<Demande> findByStatus(String status);
    List<Demande> findByUtilisateurId(Long id);
    List<Demande> findByProjetId(Long id);
    void deleteAllByProjetId(Long id);

}
