package com.studentzone.app.user.dto.mapper;

import com.studentzone.app.user.dto.request.UserCreateRequestDTO;
import com.studentzone.app.user.dto.response.UserDetailedResponseDTO;
import com.studentzone.app.user.dto.response.UserPointsResponseDTO;
import com.studentzone.app.user.dto.response.UserSavedBackgroundResponseDTO;
import com.studentzone.app.user.dto.response.UserSavedMusicResponseDTO;
import com.studentzone.app.user.dto.response.UserTimesResponseDTO;
import com.studentzone.app.user.entity.UserEntity;

public class UserMapper {
    public static UserEntity toEntity(UserCreateRequestDTO request) {
        return UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }

    public static UserDetailedResponseDTO toUserDetailedResponseDTO(UserEntity user) {
        return UserDetailedResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .totalPoints(user.getTotalPoints())
                .totalTimes(user.getTotalTimes())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

    public static UserPointsResponseDTO toUserPointsResponseDTO(UserEntity user) {
        return UserPointsResponseDTO.builder()
        .id(user.getId())
        .totalPoints(user.getTotalPoints())
        .build();
    }

    public static UserTimesResponseDTO  toUserTimesResponseDTO(UserEntity user) {
        return UserTimesResponseDTO.builder()
        .id(user.getId())
        .totalTimes(user.getTotalTimes())
        .build();
    }

    public static UserSavedMusicResponseDTO toUserSavedMusicResponseDTO(UserEntity user) {
        return UserSavedMusicResponseDTO.builder()
        .id(user.getId())
        .savedMusicUrls(user.getSavedMusic().stream()
                .map(m -> m.getMusic().getMusicUrl())
                .toList())
        .build();
    }

    public static UserSavedBackgroundResponseDTO toUserSavedBackgroundResponseDTO(UserEntity user) {
        return UserSavedBackgroundResponseDTO.builder()
        .id(user.getId())
        .savedBackgroundUrls(user.getSavedBackgrounds().stream()
                .map(b -> b.getBackground().getImageUrl())
                .toList())
        .build();
    }

    private UserMapper() {}
}
