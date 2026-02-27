package com.studentzone.app.study.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudySessionResponseDTO {
    private Long id;
    private Long userId;
    private Double pointsEarned;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
