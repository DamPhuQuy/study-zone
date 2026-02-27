package com.studentzone.app.music.dto.mapper;

import com.studentzone.app.music.dto.response.MusicSavedResponseDTO;
import com.studentzone.app.music.dto.response.MusicUnlockedResponseDTO;
import com.studentzone.app.music.entity.MusicSavedEntity;
import com.studentzone.app.music.entity.MusicUnlockedEntity;

public class MusicMapper {
    public static MusicSavedResponseDTO toSavedDto(MusicSavedEntity musicSaved) {
        return MusicSavedResponseDTO.builder()
                                    .id(musicSaved.getId())
                                    .userId(musicSaved.getUser().getId())
                                    .musicId(musicSaved.getMusic().getId())
                                    .savedAt(musicSaved.getSavedAt())
                                    .build();
    }

    public static MusicUnlockedResponseDTO toUnlockedDto(MusicUnlockedEntity musicUnlocked) {
        return MusicUnlockedResponseDTO.builder()
                                       .id(musicUnlocked.getId())
                                       .userId(musicUnlocked.getUser().getId())
                                       .musicId(musicUnlocked.getMusic().getId())
                                       .unlockedAt(musicUnlocked.getUnlockedAt())
                                       .build();
    }
    private MusicMapper() {}
}
