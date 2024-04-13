package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepos extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE YEAR(p.date) = :year ORDER BY p.nbLikes DESC, p.date DESC")
    List<Post> findPostsByYear(@Param("year") int year);

    @Query("SELECT p FROM Post p WHERE MONTH(p.date) = :month AND YEAR(p.date) = :year ORDER BY p.nbLikes DESC, p.date DESC")
    List<Post> findPostsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT p FROM Post p WHERE DAY(p.date) = :day AND MONTH(p.date) = :month AND YEAR(p.date) = :year ORDER BY p.nbLikes DESC, p.date DESC")
    List<Post> findPostsByDay(@Param("day") int day, @Param("month") int month, @Param("year") int year);

}
