package com.studentzone.app.music.service;

import com.studentzone.app.music.repository.MusicRepository;
import com.studentzone.app.music.repository.MusicSavedRepository;
import com.studentzone.app.music.repository.MusicUnlockedRepository;

public class MusicService {
    private final MusicRepository musicRepository;
    private final MusicSavedRepository musicSavedRepository;
    private final MusicUnlockedRepository musicUnlockedRepository;

    public MusicService(MusicRepository musicRepository, MusicSavedRepository musicSavedRepository,
            MusicUnlockedRepository musicUnlockedRepository) {
        this.musicRepository = musicRepository;
        this.musicSavedRepository = musicSavedRepository;
        this.musicUnlockedRepository = musicUnlockedRepository;
    }
}
