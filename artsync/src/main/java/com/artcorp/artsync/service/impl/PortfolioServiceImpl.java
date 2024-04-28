package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Portfolio;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.PortfolioRepos;
import com.artcorp.artsync.service.PortfolioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepos portfolioRepos;
    @Autowired
    public PortfolioServiceImpl(PortfolioRepos portfolioRepos) {
        this.portfolioRepos = portfolioRepos;
    }
    @Override
    public Portfolio createPortfolio(Portfolio portfolio) {
        Portfolio existingPortfolio = portfolioRepos.findByUtilisateur(portfolio.getUtilisateur());
        if (existingPortfolio != null) {
            portfolioRepos.deletePortfolioByUserId(portfolio.getUtilisateur().getId());
        }
        try {
            return portfolioRepos.save(portfolio);
        } catch (DataIntegrityViolationException e) {
            System.err.println("Échec de création de portfolio, conflit de duplication de clé: " + e.getMessage());

            throw new RuntimeException("Échec de création de portfolio, conflit de duplication de clé", e);
        }
    }
    @Override
    public Portfolio findByUtilisateur(Utilisateur utilisateur) {
        return portfolioRepos.findByUtilisateur(utilisateur);
    }

    @Override
    public void deletePortfolio(Portfolio portfolio) {
        portfolioRepos.delete(portfolio);
    }
}
