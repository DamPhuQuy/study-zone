package com.studentzone.app.background.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studentzone.app.background.entity.BackgroundSavedEntity;

public interface BackgroundSavedRepository extends JpaRepository<BackgroundSavedEntity, Long> {
    BackgroundSavedEntity save(BackgroundSavedEntity backgroundSaved);
    
    Optional<BackgroundSavedEntity> findById(Long id);

    @Query("select bs from BackgroundSavedEntity bs where bs.user.id = :userId and bs.background.id = :backgroundId")
    Optional<BackgroundSavedEntity> findByUserIdAndBackgroundId(@Param("userId") Long userId, @Param("backgroundId") Long backgroundId);

    @Query("select bs from BackgroundSavedEntity bs where bs.user.id = :userId")
    List<BackgroundSavedEntity> findByUserId(@Param("userId") Long userId);

    @Query("select bs from BackgroundSavedEntity bs where bs.background.id = :backgroundId")
    List<BackgroundSavedEntity> findByBackgroundId(@Param("backgroundId") Long backgroundId);
}
