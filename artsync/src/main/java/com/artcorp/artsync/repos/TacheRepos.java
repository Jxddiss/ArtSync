package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepos extends JpaRepository<Tache, Long> {
    public Tache findByTitre(String titre);
    public Tache findByUtilisateurId(Long id);
    public List<Tache> findByEtat(String etat);
    public List<Tache> findByProjetId(Long id);
    public List<Tache> findByUtilisateurIdAndProjetId(Long utilisateurId, Long projetId);
    public List<Tache> findByEtatAndProjetId(String etat, Long projetId);

}
