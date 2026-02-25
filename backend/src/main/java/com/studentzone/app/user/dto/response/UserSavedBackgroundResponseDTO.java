package com.studentzone.app.user.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSavedBackgroundResponseDTO {
    private Long id;
    private List<String> savedBackgroundUrls;
}
