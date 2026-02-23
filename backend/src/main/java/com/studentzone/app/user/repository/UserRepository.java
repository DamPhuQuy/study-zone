package com.studentzone.app.user.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentzone.app.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // insert user
    // jpql: insert into UserEntity (username, email, password, totalPoints, totalTimes, avatarUrl, createdAt, updatedAt) values (:username, :email, :password, :totalPoints, :totalTimes, :avatarUrl, :createdAt, :updatedAt)
    <S extends UserEntity> S save(S user);

    // Basic finders
    // jpql: select u from UserEntity u where u.username = :username
    Optional<UserEntity> findByUsername(String username);

    // jpql: select u from UserEntity u where u.email = :email
    Optional<UserEntity> findByEmail(String email);

    // Existence checks
    // jpql: select count(u) > 0 from UserEntity u where u.username = :username
    boolean existsByUsername(String username);

    // jpql: select count(u) > 0 from UserEntity u where u.email = :email
    boolean existsByEmail(String email);

    // Search functionality
    // jpql: select u from UserEntity u where lower(u.username) like lower(concat("%", :username, "%"))
    List<UserEntity> findByUsernameContainingIgnoreCase(String username);

    // Leaderboard queries - ordered by points
    // jpql: select u from UserEntity u order by u.totalPoints desc
    List<UserEntity> findAllByOrderByTotalPointsDesc();

    // jpql: select u from UserEntity u order by u.totalPoints desc, u.totalTimes desc
    List<UserEntity> findAllByOrderByTotalPointsDescTotalTimesDesc();

    // jpql: select u from UserEntity u order by u.totalPoints desc
    Page<UserEntity> findAllByOrderByTotalPointsDesc(Pageable pageable);

    // Find top N users by points
    // jpql: select u from UserEntity u order by u.totalPoints desc limit 10 offset 0
    List<UserEntity> findTop10ByOrderByTotalPointsDesc();

    // Filter by points threshold
    // jpql: select u from UserEntity u where u.totalPoints >= :points
    List<UserEntity> findByTotalPointsGreaterThanEqual(Long points);

    // Filter by time threshold
    // jpql: select u from UserEntity u where u.totalTimes >= :times
    List<UserEntity> findByTotalTimesGreaterThanEqual(Long times);

    // Date range queries
    // jpql: select u from UserEntity u where u.createdAt > :date
    List<UserEntity> findByCreatedAtAfter(LocalDateTime date);

    // jpql: select u from UserEntity u where u.createdAt < :date
    List<UserEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // find users with their unlocked items count
    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.unlockedMusic LEFT JOIN FETCH u.unlockedBackgrounds WHERE u.id = :userId")
    Optional<UserEntity> findByIdWithUnlockedItems(@Param("userId") Long userId);

    // find users with their saved items
    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.savedMusic LEFT JOIN FETCH u.savedBackgrounds WHERE u.id = :userId")
    Optional<UserEntity> findByIdWithSavedItems(@Param("userId") Long userId);

    // Find users by points range
    // jpql: select u from UserEntity u where u.totalPoints between :minPoints and :maxPoints
    List<UserEntity> findByTotalPointsBetween(Long minPoints, Long maxPoints);

    // Count users by criteria
    // jpql: select count(u) from UserEntity u where u.createdAt > :date
    long countByCreatedAtAfter(LocalDateTime date);

    // Update username
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.username = :username, u.updatedAt = :updatedAt WHERE u.id = :userId")
    int updateUsername(@Param("userId") Long userId, @Param("username") String username, @Param("updatedAt") LocalDateTime updatedAt);

    // Update email
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.email = :email, u.updatedAt = :updatedAt WHERE u.id = :userId")
    int updateEmail(@Param("userId") Long userId, @Param("email") String email, @Param("updatedAt") LocalDateTime updatedAt);

    // Update avatar URL
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.avatarUrl = :avatarUrl, u.updatedAt = :updatedAt WHERE u.id = :userId")
    int updateAvatarUrl(@Param("userId") Long userId, @Param("avatarUrl") String avatarUrl, @Param("updatedAt") LocalDateTime updatedAt);

    // Update total points
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.totalPoints = :totalPoints, u.updatedAt = :updatedAt WHERE u.id = :userId")
    int updateTotalPoints(@Param("userId") Long userId, @Param("totalPoints") Long totalPoints, @Param("updatedAt") LocalDateTime updatedAt);

    // Update total times
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.totalTimes = :totalTimes, u.updatedAt = :updatedAt WHERE u.id = :userId")
    int updateTotalTimes(@Param("userId") Long userId, @Param("totalTimes") Long totalTimes, @Param("updatedAt") LocalDateTime updatedAt);

    // Increment total points
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.totalPoints = u.totalPoints + :points, u.updatedAt = :updatedAt WHERE u.id = :userId")
    int incrementTotalPoints(@Param("userId") Long userId, @Param("points") Long points, @Param("updatedAt") LocalDateTime updatedAt);

    // Increment total times
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.totalTimes = u.totalTimes + :times, u.updatedAt = :updatedAt WHERE u.id = :userId")
    int incrementTotalTimes(@Param("userId") Long userId, @Param("times") Long times, @Param("updatedAt") LocalDateTime updatedAt);

    // Update user profile (username, email, avatar)
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.username = :username, u.email = :email, u.avatarUrl = :avatarUrl, u.updatedAt = :updatedAt WHERE u.id = :userId")
    int updateProfile(@Param("userId") Long userId, @Param("username") String username,
                      @Param("email") String email, @Param("avatarUrl") String avatarUrl,
                      @Param("updatedAt") LocalDateTime updatedAt);

    // Update password
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.password = :password, u.updatedAt = :updatedAt WHERE u.id = :userId")
    int updatePassword(@Param("userId") Long userId, @Param("password") String password, @Param("updatedAt") LocalDateTime updatedAt);

    // Batch update updatedAt timestamp
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.updatedAt = :updatedAt WHERE u.id IN :userIds")
    int batchUpdateTimestamp(@Param("userIds") List<Long> userIds, @Param("updatedAt") LocalDateTime updatedAt);

    // Delete by username
    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.username = :username")
    int deleteByUsername(@Param("username") String username);

    // Delete by email
    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.email = :email")
    int deleteByEmail(@Param("email") String email);

    // Delete users with zero points (cleanup inactive users)
    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.totalPoints = 0 OR u.totalPoints IS NULL")
    int deleteUsersWithZeroPoints();

    // Delete users created before a certain date (cleanup old inactive accounts)
    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.createdAt < :date AND (u.totalPoints = 0 OR u.totalPoints IS NULL)")
    int deleteInactiveUsersBefore(@Param("date") LocalDateTime date);

    // Batch delete by IDs
    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.id IN :userIds")
    int batchDeleteByIds(@Param("userIds") List<Long> userIds);
}
