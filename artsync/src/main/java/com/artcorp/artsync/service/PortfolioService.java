package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioService extends JpaRepository<Portfolio,Long> {
}
