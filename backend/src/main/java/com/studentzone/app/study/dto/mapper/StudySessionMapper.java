package com.studentzone.app.study.dto.mapper;

import com.studentzone.app.study.dto.request.StudySessionCreationRequestDTO;
import com.studentzone.app.study.dto.response.StudySessionResponseDTO;
import com.studentzone.app.study.entity.StudySessionEntity;
import com.studentzone.app.user.entity.UserEntity;

public class StudySessionMapper {
    public static StudySessionEntity toEntity(StudySessionCreationRequestDTO request, UserEntity user) {
        return StudySessionEntity.builder()
                .startTime(request.getStartTime())
                .user(user)
                .pointsEarned(0L)
                .diffTime(0L)
                .build();
    }

    public static StudySessionResponseDTO toDTO(StudySessionEntity studySessionEntity) {
        Long userId = studySessionEntity.getUser() != null ? studySessionEntity.getUser().getId() : null;

        return StudySessionResponseDTO.builder()
                .id(studySessionEntity.getId())
                .userId(userId)
                .pointsEarned(studySessionEntity.getPointsEarned())
                .diffTime(studySessionEntity.getDiffTime())
                .startTime(studySessionEntity.getStartTime())
                .build();
    }

    private StudySessionMapper() {}
}
