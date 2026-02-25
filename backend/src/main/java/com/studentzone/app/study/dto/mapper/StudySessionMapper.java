package com.studentzone.app.study.dto.mapper;

import com.studentzone.app.study.dto.response.StudySessionResponseDTO;
import com.studentzone.app.study.entity.StudySessionEntity;

public class StudySessionMapper {

    public static StudySessionResponseDTO toDTO(StudySessionEntity studySessionEntity) {
        Long userId = studySessionEntity.getUser() != null ? studySessionEntity.getUser().getId() : null;

        return StudySessionResponseDTO.builder()
                .id(studySessionEntity.getId())
                .userId(userId)
                .pointsEarned(studySessionEntity.getPointsEarned())
                .startTime(studySessionEntity.getStartTime())
                .endTime(studySessionEntity.getEndTime())
                .build();
    }

    private StudySessionMapper() {}
}
