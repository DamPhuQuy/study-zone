package com.studentzone.app.user.dto.request;

import com.studentzone.app.user.entity.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateRequestDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private Role role;
}
