package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Forum;
import com.artcorp.artsync.entity.Conversation;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepos extends JpaRepository<Forum, Long>{
  List<Forum> findByPubliqueTrue();
  @Query("select f from Forum f where f.titre like %?1% and f.publique = true")
  List<Forum> findByKeyword(String keyword);
  List<Forum> findAllByUtilisateur(Utilisateur utilisateur);
  List<Forum> findAllByUtilisateurFollowing(Utilisateur utilisateur);



}
