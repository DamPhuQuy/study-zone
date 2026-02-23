package com.studentzone.app.music.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicInfoResponseDTO {
    private Long id;
    private String name;
    private String musicUrl;
    private Long duration;
    private Long requiredPoint;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<MusicSavedInfoResponseDTO> savedByUsers;
    private List<MusicUnlockedInfoResponseDTO> unlockedByUsers;
}
