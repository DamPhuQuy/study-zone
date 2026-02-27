package com.studentzone.app.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailedResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Double totalPoints;
    private Long totalTimes;
    private String avatarUrl;
}
