package com.studentzone.app.user.dto.response;

import java.util.List;

import com.studentzone.app.study.dto.response.StudySessionResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailedResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Long totalPoints;
    private Long totalTimes;
    private String avatarUrl;
    private List<StudySessionResponseDTO> studyHistory;
}
