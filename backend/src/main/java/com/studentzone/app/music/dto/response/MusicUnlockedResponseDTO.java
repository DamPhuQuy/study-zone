package com.studentzone.app.music.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicUnlockedResponseDTO {
    private Long id;
    private Long userId;
    private Long musicId;
    private LocalDateTime unlockedAt;
}
