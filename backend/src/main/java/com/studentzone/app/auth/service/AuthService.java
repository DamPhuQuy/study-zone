package com.studentzone.app.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studentzone.app.auth.dto.AuthResponseDTO;
import com.studentzone.app.auth.dto.LoginRequestDTO;
import com.studentzone.app.auth.dto.RegisterRequestDTO;
import com.studentzone.app.common.security.JwtUtil;
import com.studentzone.app.user.entity.Role;
import com.studentzone.app.user.entity.UserEntity;
import com.studentzone.app.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use: " + request.getEmail());
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .totalPoints(0.0)
                .totalTimes(0L)
                .role(Role.USER)
                .build();

        UserEntity saved = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(saved.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return AuthResponseDTO.of(token, saved.getId(), saved.getUsername(), saved.getEmail(), saved.getRole().name());
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return AuthResponseDTO.of(token, user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }
}
