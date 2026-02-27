package com.studentzone.app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String accessToken;
    private String tokenType;
    private Long userId;
    private String username;
    private String email;
    private String role;

    public static AuthResponseDTO of(String token, Long userId, String username, String email, String role) {
        return AuthResponseDTO.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(userId)
                .username(username)
                .email(email)
                .role(role)
                .build();
    }
}
