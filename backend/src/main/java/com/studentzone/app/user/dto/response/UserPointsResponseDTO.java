package com.studentzone.app.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPointsResponseDTO {
    private Long id;
    private Double totalPoints;
}
