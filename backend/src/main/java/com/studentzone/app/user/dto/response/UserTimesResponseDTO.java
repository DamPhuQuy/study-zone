package com.studentzone.app.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTimesResponseDTO {
    private Long id;
    private Long totalTimes;
}
