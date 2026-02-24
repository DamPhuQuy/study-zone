package com.studentzone.app.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentzone.app.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity save(UserEntity user);

    Optional<UserEntity> findByUserId(Long id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findAll();

    long count();

    void delete(UserEntity user);

    void deleteById(Long id);

    void deleteByUsername(String username);

    void deleteByEmail(String email);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    // custom query methods
    @Query("select u from UserEntity u order by u.totalPoints desc")
    List<UserEntity> findByOrderByTotalPointsDesc();

    @Query("select u from UserEntity u order by u.totalTimes desc")
    List<UserEntity> findByOrderByTotalTimesDesc();
}
