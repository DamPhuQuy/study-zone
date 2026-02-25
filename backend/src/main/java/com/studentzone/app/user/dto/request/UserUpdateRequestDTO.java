package com.studentzone.app.user.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequestDTO {
    private String username;
    private String avatarUrl;
}
