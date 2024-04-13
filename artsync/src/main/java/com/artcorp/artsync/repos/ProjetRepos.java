package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepos extends JpaRepository<Projet, Long> {
     public Projet findByTitre(String titre);
     public List<Projet> findByPublique(boolean publique);
     public List<Projet> findByUtilisateursId(Long id);
     @Query("select p from Projet p where p.titre like %?1%")
     public List<Projet> findByKeyword(String keyword);
     @Query("select count(p) from Projet p join p.utilisateurs u where p.id = ?1 and u.id = ?2")
     public int checkIfUserIsInProjet(Long idProjet, Long idUtilisateur);

}
