package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface PostRepos extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE YEAR(p.date) = :year ORDER BY p.nbLikes DESC, p.date DESC")
    List<Post> findPostsByYear(@Param("year") int year);

    @Query("SELECT p FROM Post p WHERE MONTH(p.date) = :month AND YEAR(p.date) = :year ORDER BY p.nbLikes DESC, p.date DESC")
    List<Post> findPostsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT p FROM Post p WHERE DAY(p.date) = :day AND MONTH(p.date) = :month AND YEAR(p.date) = :year ORDER BY p.nbLikes DESC, p.date DESC")
    List<Post> findPostsByDay(@Param("day") int day, @Param("month") int month, @Param("year") int year);

    @Query("SELECT p FROM Post p WHERE p.utilisateur IN :followingList ORDER BY p.date DESC")
    List<Post> findPostFollowing(@Param("followingList") Set<Utilisateur> followingList);

    @Query("SELECT p FROM Post p ORDER BY p.nbLikes DESC LIMIT 19")
    List<Post> findPostsEnVedette();

    List<Post> findAllByUtilisateurAndPublique(Utilisateur utilisateur,boolean publique);

    @Query("SELECT p FROM Post p WHERE p.publique = :publique AND p.type != 'video' ORDER BY p.nbLikes DESC LIMIT 19")
    List<Post> findByPubliqueEnVedette(@Param("publique") boolean publique);

    @Query("SELECT p from Post p WHERE p.publique = :publique ORDER BY p.date DESC")
    List<Post> findAllPostsPublique(@Param("publique") boolean publique);

    @Query("SELECT p FROM Post p WHERE p.type = :type AND p.publique = :publique ORDER BY p.nbLikes DESC LIMIT 19")
    List<Post> findByTypeEnVedettePublique(@Param("type") String type, @Param("publique") boolean publique);

    @Query("SELECT p FROM Post p WHERE p.type = :type AND p.publique = :publique ORDER BY p.date DESC")
    List<Post> findAllPostsByTypePublique(@Param("type") String type, @Param("publique") boolean publique);

    @Query("SELECT p FROM Post p WHERE p.type = :type ORDER BY p.nbLikes DESC LIMIT 19")
    List<Post> findByTypeEnVedette(@Param("type") String type);

    @Query("SELECT p FROM Post p WHERE p.type = :type ORDER BY p.date DESC")
    List<Post> findAllPostsByType(@Param("type") String type);

    @Query("SELECT p FROM Post p WHERE p.utilisateur = :utilisateur ORDER BY p.date DESC")
    List<Post> findPostByUser(@Param("utilisateur") Utilisateur id);

    @Query("SELECT p FROM Post p WHERE p.type = 'Banniere' AND p.utilisateur = :utilisateur")
    Post findBanniereUtilisateur(@Param("utilisateur") Utilisateur utilisateur);
    @Query("SELECT p FROM Post p ORDER BY p.nbLikes DESC LIMIT 10")
    List<Post> findTop10Posts();

    @Query("SELECT p FROM Post p WHERE p.utilisateur IN :followingList AND p.utilisateur IN :followerList AND p.type != 'video' ORDER BY p.date DESC LIMIT 15")
    List<Post> findPostFollowingAndFollower(@Param("followingList") Set<Utilisateur> followingList, @Param("followerList") Set<Utilisateur> followerList);

    @Query("SELECT p FROM Post p WHERE p.type != 'video'")
    List<Post> findAllByTypeNotVideo();
}
