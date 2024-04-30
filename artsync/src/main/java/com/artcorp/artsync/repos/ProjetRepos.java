package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Tache;
import com.artcorp.artsync.entity.Utilisateur;
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

     @Query("select p from Projet p join p.utilisateurs u where u.id = ?1")
     public List<Projet> findProjectsOfUser(Long idUtilisateur);

     @Query("select count(u) from Projet p join p.utilisateurs u where p.id = ?1")
     public int getMembersCount(Long idProjet);

     @Query("select u from Projet p join p.utilisateurs u where p.id = ?1")
     public List<Utilisateur> getMembers(Long idProjet);


     @Query("select count(f) from Projet p join p.fichiers f where p.id = ?1")
     public int getFileCount(Long idProjet);
     @Query("select u from Projet p join p.utilisateurs u where p.id = ?1 and (u.pseudo like %?2% or u.prenom like %?2% or u.nom like %?2%)")
     public List<Utilisateur> findUsersOfProjectByKeyword(Long idprojet,String keyword);
     public void deleteById(Long id);
}
