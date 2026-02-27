package com.studentzone.app.background.dto.mapper;

import com.studentzone.app.background.dto.response.BackgroundSavedResponseDTO;
import com.studentzone.app.background.dto.response.BackgroundUnlockedResponseDTO;
import com.studentzone.app.background.entity.BackgroundSavedEntity;
import com.studentzone.app.background.entity.BackgroundUnlockedEntity;

public class BackgroundMapper {
    public static BackgroundSavedResponseDTO toBackgroundSavedResponseDTO(BackgroundSavedEntity entity) {
        return BackgroundSavedResponseDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .backgroundId(entity.getBackground().getId())
                .savedAt(entity.getSavedAt())
                .build();
    }

    public static BackgroundUnlockedResponseDTO toBackgroundUnlockedResponseDTO(BackgroundUnlockedEntity entity) {
        return BackgroundUnlockedResponseDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .backgroundId(entity.getBackground().getId())
                .unlockedAt(entity.getUnlockedAt())
                .build();
    }

    private BackgroundMapper() {}
}
