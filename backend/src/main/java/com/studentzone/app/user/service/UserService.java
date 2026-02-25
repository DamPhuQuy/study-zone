package com.studentzone.app.user.service;

import org.springframework.stereotype.Service;

import com.studentzone.app.user.dto.mapper.UserMapper;
import com.studentzone.app.user.dto.request.UserCreateRequestDTO;
import com.studentzone.app.user.dto.request.UserUpdateRequestDTO;
import com.studentzone.app.user.dto.response.UserDetailedResponseDTO;
import com.studentzone.app.user.dto.response.UserPointsResponseDTO;
import com.studentzone.app.user.dto.response.UserSavedBackgroundResponseDTO;
import com.studentzone.app.user.dto.response.UserSavedMusicResponseDTO;
import com.studentzone.app.user.dto.response.UserTimesResponseDTO;
import com.studentzone.app.user.entity.UserEntity;
import com.studentzone.app.user.repository.UserRepository;

@Service
public class UserService {
    private static final String USER_NOT_FOUND_MESSAGE = "User not found with id: ";
    private final UserRepository userRepository;

    public void createUser(UserCreateRequestDTO request) {
        UserEntity user = UserMapper.toEntity(request);

        userRepository.save(user);
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDetailedResponseDTO getUserDetailsById(Long id) {
        UserEntity user = userRepository.findByIdWithSessions(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + id));

        return UserMapper.toUserDetailedResponseDTO(user);
    }

    public UserPointsResponseDTO getUserPointsById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + id));

        return UserMapper.toUserPointsResponseDTO(user);
    }

    public UserTimesResponseDTO getUserTimesById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + id));

        return UserMapper.toUserTimesResponseDTO(user);
    }

    public UserSavedMusicResponseDTO getUserSavedMusicResponseDTO(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + id));

        return UserMapper.toUserSavedMusicResponseDTO(user);
    }

    public UserSavedBackgroundResponseDTO getUserSavedBackgroundResponseDTO(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + id));

        return UserMapper.toUserSavedBackgroundResponseDTO(user);
    }

    public void updateUserProfile(Long id, UserUpdateRequestDTO request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + id));

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        userRepository.save(user);
    }
}
