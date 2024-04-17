package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Portfolio;
import com.artcorp.artsync.entity.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepos extends JpaRepository<Portfolio, Long> {
    public Portfolio findByUtilisateurId(Long id);

    Portfolio findByUtilisateur(Utilisateur utilisateur);
    @Transactional
    @Modifying
    @Query("DELETE FROM Portfolio p WHERE p.utilisateur.id = :userId")
    void deletePortfolioByUserId(@Param("userId") Long userId);
}
