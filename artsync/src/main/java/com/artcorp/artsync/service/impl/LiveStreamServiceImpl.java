package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.LiveStream;
import com.artcorp.artsync.repos.LiveStreamRepos;
import com.artcorp.artsync.service.LiveStreamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LiveStreamServiceImpl implements LiveStreamService {
    private LiveStreamRepos liveStreamRepos;

    @Autowired
    public LiveStreamServiceImpl(LiveStreamRepos liveStreamRepos) {
        this.liveStreamRepos = liveStreamRepos;
    }

    @Override
    public List<LiveStream> getAllActiveLiveStream() {
        return liveStreamRepos.findAllByActive(true);
    }

    @Override
    public LiveStream findByPseudo(String pseudo) {
        return liveStreamRepos.findByPseudoStreamer(pseudo);
    }

    @Override
    public LiveStream addLive(LiveStream liveStream) {
        return liveStreamRepos.save(liveStream);
    }

    @Override
    public List<LiveStream> findAllByKeyword(String keyword) {
        return liveStreamRepos.findAllByKeyword(keyword);
    }
}
