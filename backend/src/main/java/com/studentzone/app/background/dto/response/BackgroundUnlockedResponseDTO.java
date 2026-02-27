package com.studentzone.app.background.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BackgroundUnlockedResponseDTO {
    private Long id;
    private Long userId;
    private Long backgroundId;
    private LocalDateTime unlockedAt;
}
