package com.studentzone.app.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.studentzone.app.user.entity.UserEntity;
import com.studentzone.app.user.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// create

	public UserEntity createUser(UserEntity user) {
		LocalDateTime now = LocalDateTime.now();
		if (user.getCreatedAt() == null) {
			user.setCreatedAt(now);
		}
		user.setUpdatedAt(now);
		if (user.getTotalPoints() == null) {
			user.setTotalPoints(0L);
		}
		if (user.getTotalTimes() == null) {
			user.setTotalTimes(0L);
		}
		return userRepository.save(user);
	}

	// read

	public Optional<UserEntity> findById(Long userId) {
		return userRepository.findById(userId);
	}

	public Optional<UserEntity> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public List<UserEntity> searchByUsername(String username) {
		return userRepository.findByUsernameContainingIgnoreCase(username);
	}

	public List<UserEntity> getLeaderboardByPoints() {
		return userRepository.findAllByOrderByTotalPointsDesc();
	}

	public List<UserEntity> getLeaderboardByPointsAndTimes() {
		return userRepository.findAllByOrderByTotalPointsDescTotalTimesDesc();
	}

	public Page<UserEntity> getLeaderboardByPoints(Pageable pageable) {
		return userRepository.findAllByOrderByTotalPointsDesc(pageable);
	}

	public List<UserEntity> getTop10ByPoints() {
		return userRepository.findTop10ByOrderByTotalPointsDesc();
	}

	public List<UserEntity> findByMinPoints(Long points) {
		return userRepository.findByTotalPointsGreaterThanEqual(points);
	}

	public List<UserEntity> findByMinTimes(Long times) {
		return userRepository.findByTotalTimesGreaterThanEqual(times);
	}

	public List<UserEntity> findCreatedAfter(LocalDateTime date) {
		return userRepository.findByCreatedAtAfter(date);
	}

	public List<UserEntity> findCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return userRepository.findByCreatedAtBetween(startDate, endDate);
	}

	public Optional<UserEntity> findByIdWithUnlockedItems(Long userId) {
		return userRepository.findByIdWithUnlockedItems(userId);
	}

	public Optional<UserEntity> findByIdWithSavedItems(Long userId) {
		return userRepository.findByIdWithSavedItems(userId);
	}

	public List<UserEntity> findByPointsBetween(Long minPoints, Long maxPoints) {
		return userRepository.findByTotalPointsBetween(minPoints, maxPoints);
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
