package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepos extends JpaRepository<Portfolio, Long> {
    public Portfolio findByUtilisateurId(Long id);
}
