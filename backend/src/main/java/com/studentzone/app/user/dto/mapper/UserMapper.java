package com.studentzone.app.user.dto.mapper;

import java.time.LocalDateTime;

import com.studentzone.app.user.dto.request.UserCreateRequest;
import com.studentzone.app.user.dto.response.UserInfoResponse;
import com.studentzone.app.user.entity.UserEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

	public static UserEntity toEntity(UserCreateRequest request) {
		if (request == null) {
			return null;
		}
		return UserEntity.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(request.getPassword())
				.totalPoints(0L)
				.totalTimes(0L)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
	}

	public static UserInfoResponse toResponse(UserEntity user) {
		if (user == null) {
			return null;
		}
		return UserInfoResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.email(user.getEmail())
				.totalPoints(user.getTotalPoints())
				.totalTimes(user.getTotalTimes())
				.avatarUrl(user.getAvatarUrl())
				.createdAt(user.getCreatedAt())
				.updatedAt(user.getUpdatedAt())
				.build();
	}
}
