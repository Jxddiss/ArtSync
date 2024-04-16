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
    public void flush() {

    }

    @Override
    public <S extends Portfolio> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Portfolio> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Portfolio> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Portfolio getOne(Long aLong) {
        return null;
    }

    @Override
    public Portfolio getById(Long aLong) {
        return null;
    }

    @Override
    public Portfolio getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Portfolio> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Portfolio> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Portfolio> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Portfolio> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Portfolio> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Portfolio> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Portfolio, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Portfolio> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Portfolio> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Portfolio> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Portfolio> findAll() {
        return null;
    }

    @Override
    public List<Portfolio> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Portfolio entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Portfolio> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Portfolio> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Portfolio> findAll(Pageable pageable) {
        return null;
    }

    public Portfolio findByUtilisateur(Utilisateur utilisateur) {
        return portfolioRepos.findByUtilisateur(utilisateur);
    }
}
