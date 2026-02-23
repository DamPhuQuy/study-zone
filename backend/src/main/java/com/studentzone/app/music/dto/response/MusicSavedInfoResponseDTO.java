package com.studentzone.app.music.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicSavedInfoResponseDTO {
    private Long id;
    private Long userId;
    private Long musicId;
    private LocalDateTime savedAt;
}
