package com.studentzone.app.study.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudySessionCreationRequestDTO {
    private Long userId;
    private LocalDateTime startTime;
}
