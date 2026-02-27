package com.studentzone.app.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentzone.app.auth.dto.AuthResponseDTO;
import com.studentzone.app.auth.dto.LoginRequestDTO;
import com.studentzone.app.auth.dto.RegisterRequestDTO;
import com.studentzone.app.auth.service.AuthService;
import com.studentzone.app.common.response.ApiResponseCommon;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseCommon<AuthResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO request) {
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseCommon.success(response, "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseCommon<AuthResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiResponseCommon.success(response, "Login successful"));
    }
}
