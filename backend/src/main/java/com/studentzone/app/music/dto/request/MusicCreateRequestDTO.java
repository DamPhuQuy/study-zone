package com.studentzone.app.music.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicCreateRequestDTO {
    private String name;
    private String musicUrl;
    private Long duration;
    private Long requiredPoint;
}
