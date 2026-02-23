package com.studentzone.app.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequestDTO {
    @Size(min = 3, message = "Username must be at least 3 characters")
    private String username;

    @Email(message = "Email is invalid")
    private String email;

    private String avatarUrl;
}
