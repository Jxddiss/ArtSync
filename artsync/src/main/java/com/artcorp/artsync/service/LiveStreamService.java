package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.LiveStream;

import java.util.List;

public interface LiveStreamService {
    List<LiveStream> getAllActiveLiveStream();
    LiveStream findByPseudo(String pseudo);
    LiveStream addLive(LiveStream liveStream);
}
