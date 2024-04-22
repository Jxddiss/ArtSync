package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepos extends JpaRepository<Annonce, Long> {
    public List<Annonce> findByProjetId(Long id);
    public void deleteAllByProjetId(Long id);
}
