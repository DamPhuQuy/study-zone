package com.studentzone.app.study.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudyEndSessionRequestDTO {
    private Long sessionId;
}
