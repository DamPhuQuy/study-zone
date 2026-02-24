package com.studentzone.app.background.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studentzone.app.background.entity.BackgroundUnlockedEntity;

public interface BackgroundUnlockedRepository extends JpaRepository<BackgroundUnlockedEntity, Long> {
    BackgroundUnlockedEntity save(BackgroundUnlockedEntity backgroundUnlocked);

    Optional<BackgroundUnlockedEntity> findById(Long id);

    @Query("select bu from BackgroundUnlockedEntity bu where bu.user.id = :userId and bu.background.id = :backgroundId")
    Optional<BackgroundUnlockedEntity> findByUserIdAndBackgroundId(@Param("userId") Long userId, @Param("backgroundId") Long backgroundId);


    @Query("select bu from BackgroundUnlockedEntity bu where bu.user.id = :userId")
    Optional<BackgroundUnlockedEntity> findByUserId(@Param("userId") Long userId);

    @Query("select bu from BackgroundUnlockedEntity bu where bu.background.id = :backgroundId")
    Optional<BackgroundUnlockedEntity> findByBackgroundId(@Param("backgroundId") Long backgroundId);
}
