package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Portfolio;
import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioService {
    Portfolio createPortfolio(Portfolio portfolio);
    Portfolio findByUtilisateur(Utilisateur utilisateur);
    void deletePortfolio(Portfolio portfolio);
}
