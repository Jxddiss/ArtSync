package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepos extends JpaRepository<Projet, Long> {
     public Projet findByTitre(String titre);
     public List<Projet> findByPublique(boolean publique);
     public List<Projet> findByUtilisateursId(Long id);

}
