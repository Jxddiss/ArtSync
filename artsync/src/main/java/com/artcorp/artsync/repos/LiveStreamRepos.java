package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.LiveStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveStreamRepos extends JpaRepository<LiveStream, Long> {

    List<LiveStream> findAllByActive(boolean isActive);
    LiveStream findByPseudoStreamer(String pseudo);

    @Query("select l from LiveStream l where (l.titre like %?1% or l.pseudoStreamer like %?1%) and l.active = true")
    List<LiveStream> findAllByKeyword(String keyword);
}
