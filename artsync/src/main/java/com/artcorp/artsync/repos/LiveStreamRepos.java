package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.LiveStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveStreamRepos extends JpaRepository<LiveStream, Long> {

    List<LiveStream> findAllByActive(boolean isActive);
    LiveStream findByPseudoStreamer(String pseudo);
}
