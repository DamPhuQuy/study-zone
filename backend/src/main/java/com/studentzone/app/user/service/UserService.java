package com.studentzone.app.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.studentzone.app.user.dto.mapper.UserMapper;
import com.studentzone.app.user.dto.request.UserCreateRequest;
import com.studentzone.app.user.dto.response.UserInfoResponse;
import com.studentzone.app.user.entity.UserEntity;
import com.studentzone.app.user.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// create

	public UserInfoResponse createUser(UserCreateRequest request) {
		UserEntity user = UserMapper.toEntity(request);

        return UserMapper.toResponse(userRepository.save(user));
	}

	// read

	public Optional<UserInfoResponse> findById(Long userId) {
		return userRepository.findById(userId).map(UserMapper::toResponse);
	}

	public Optional<UserInfoResponse> findByUsername(String username) {
		return userRepository.findByUsername(username).map(UserMapper::toResponse);
	}

	public Optional<UserInfoResponse> findByEmail(String email) {
		return userRepository.findByEmail(email).map(UserMapper::toResponse);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public List<UserInfoResponse> searchByUsername(String username) {
		return userRepository.findByUsernameContainingIgnoreCase(username).stream().map(UserMapper::toResponse).toList();
	}

	public List<UserInfoResponse> getLeaderboardByPoints() {
		return userRepository.findAllByOrderByTotalPointsDesc().stream().map(UserMapper::toResponse).toList();
	}

	public List<UserInfoResponse> getLeaderboardByPointsAndTimes() {
		return userRepository.findAllByOrderByTotalPointsDescTotalTimesDesc().stream().map(UserMapper::toResponse).toList();
	}

	public Page<UserEntity> getLeaderboardByPoints(Pageable pageable) {
		return userRepository.findAllByOrderByTotalPointsDesc(pageable);
	}

	public List<UserInfoResponse> getTop10ByPoints() {
		return userRepository.findTop10ByOrderByTotalPointsDesc().stream().map(UserMapper::toResponse).toList();
	}

	public List<UserInfoResponse> findByMinPoints(Long points) {
		return userRepository.findByTotalPointsGreaterThanEqual(points).stream().map(UserMapper::toResponse).toList();
	}

	public List<UserInfoResponse> findByMinTimes(Long times) {
		return userRepository.findByTotalTimesGreaterThanEqual(times).stream().map(UserMapper::toResponse).toList();
	}

	public List<UserInfoResponse> findCreatedAfter(LocalDateTime date) {
		return userRepository.findByCreatedAtAfter(date).stream().map(UserMapper::toResponse).toList();
	}

	public List<UserInfoResponse> findCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return userRepository.findByCreatedAtBetween(startDate, endDate).stream().map(UserMapper::toResponse).toList();
	}

	public Optional<UserInfoResponse> findByIdWithUnlockedItems(Long userId) {
		return userRepository.findByIdWithUnlockedItems(userId).map(UserMapper::toResponse);
	}

	public Optional<UserInfoResponse> findByIdWithSavedItems(Long userId) {
		return userRepository.findByIdWithSavedItems(userId).map(UserMapper::toResponse);
	}

	public List<UserInfoResponse> findByPointsBetween(Long minPoints, Long maxPoints) {
		return userRepository.findByTotalPointsBetween(minPoints, maxPoints).stream().map(UserMapper::toResponse).toList();
	}

	public long countCreatedAfter(LocalDateTime date) {
		return userRepository.countByCreatedAtAfter(date);
	}

	// update

	public int updateUsername(Long userId, String username) {
		return userRepository.updateUsername(userId, username, LocalDateTime.now());
	}

	public int updateEmail(Long userId, String email) {
		return userRepository.updateEmail(userId, email, LocalDateTime.now());
	}

	public int updateAvatarUrl(Long userId, String avatarUrl) {
		return userRepository.updateAvatarUrl(userId, avatarUrl, LocalDateTime.now());
	}

	public int updateTotalPoints(Long userId, Long totalPoints) {
		return userRepository.updateTotalPoints(userId, totalPoints, LocalDateTime.now());
	}

	public int updateTotalTimes(Long userId, Long totalTimes) {
		return userRepository.updateTotalTimes(userId, totalTimes, LocalDateTime.now());
	}

	public int incrementTotalPoints(Long userId, Long points) {
		return userRepository.incrementTotalPoints(userId, points, LocalDateTime.now());
	}

	public int incrementTotalTimes(Long userId, Long times) {
		return userRepository.incrementTotalTimes(userId, times, LocalDateTime.now());
	}

	public int updateProfile(Long userId, String username, String email, String avatarUrl) {
		return userRepository.updateProfile(userId, username, email, avatarUrl, LocalDateTime.now());
	}

	public int updatePassword(Long userId, String password) {
		return userRepository.updatePassword(userId, password, LocalDateTime.now());
	}

	public int batchUpdateTimestamp(List<Long> userIds, LocalDateTime updatedAt) {
		return userRepository.batchUpdateTimestamp(userIds, updatedAt);
	}

	// delete

	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
	}

	public int deleteByUsername(String username) {
		return userRepository.deleteByUsername(username);
	}

	public int deleteByEmail(String email) {
		return userRepository.deleteByEmail(email);
	}

	public int deleteUsersWithZeroPoints() {
		return userRepository.deleteUsersWithZeroPoints();
	}

	public int deleteInactiveUsersBefore(LocalDateTime date) {
		return userRepository.deleteInactiveUsersBefore(date);
	}

	public int batchDeleteByIds(List<Long> userIds) {
		return userRepository.batchDeleteByIds(userIds);
	}
}
