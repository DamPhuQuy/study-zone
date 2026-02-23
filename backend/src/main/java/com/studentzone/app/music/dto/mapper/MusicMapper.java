package com.studentzone.app.music.dto.mapper;

import java.util.List;

import com.studentzone.app.music.dto.response.MusicInfoResponseDTO;
import com.studentzone.app.music.dto.response.MusicSavedInfoResponseDTO;
import com.studentzone.app.music.dto.response.MusicUnlockedInfoResponseDTO;
import com.studentzone.app.music.entity.MusicEntity;
import com.studentzone.app.music.entity.MusicSavedEntity;
import com.studentzone.app.music.entity.MusicUnlockedEntity;

public class MusicMapper {

    public static MusicSavedInfoResponseDTO toMusicSavedInfoResponseDTO(MusicSavedEntity entity) {
        if (entity == null) return null;
        return MusicSavedInfoResponseDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .musicId(entity.getMusic() != null ? entity.getMusic().getId() : null)
                .savedAt(entity.getSavedAt())
                .build();
    }

    public static MusicUnlockedInfoResponseDTO toMusicUnlockedInfoResponseDTO(MusicUnlockedEntity entity) {
        if (entity == null) return null;
        return MusicUnlockedInfoResponseDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .musicId(entity.getMusic() != null ? entity.getMusic().getId() : null)
                .pointsUsed(entity.getPointsUsed())
                .unlockedAt(entity.getUnlockedAt())
                .build();
    }

    public static MusicInfoResponseDTO toMusicInfoResponseDTO(MusicEntity entity) {
        if (entity == null) return null;

        List<MusicSavedInfoResponseDTO> savedByUsers = entity.getMusicSavedByUsers() != null
                ? entity.getMusicSavedByUsers().stream()
                        .map(MusicMapper::toMusicSavedInfoResponseDTO)
                        .toList()
                : null;

        List<MusicUnlockedInfoResponseDTO> unlockedByUsers = entity.getMusicUnlockedByUsers() != null
                ? entity.getMusicUnlockedByUsers().stream()
                        .map(MusicMapper::toMusicUnlockedInfoResponseDTO)
                        .toList()
                : null;

        return MusicInfoResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .musicUrl(entity.getMusicUrl())
                .duration(entity.getDuration())
                .requiredPoint(entity.getRequiredPoint())
                .isActive(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .savedByUsers(savedByUsers)
                .unlockedByUsers(unlockedByUsers)
                .build();
    }

    private MusicMapper() {}
}
