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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register and login — no token required")
@SecurityRequirements   // override global security: auth endpoints are public
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Creates a new account and returns a JWT token.")
    @PostMapping("/register")
    public ResponseEntity<ApiResponseCommon<AuthResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO request) {
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseCommon.success(response, "User registered successfully"));
    }

    @Operation(summary = "Login", description = "Authenticate with username/email and password. Returns a JWT access token — paste it into the **Authorize** button above.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseCommon<AuthResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiResponseCommon.success(response, "Login successful"));
    }
}
