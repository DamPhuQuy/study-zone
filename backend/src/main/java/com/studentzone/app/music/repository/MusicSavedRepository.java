package com.studentzone.app.music.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentzone.app.music.entity.MusicSavedEntity;

@Repository
public interface MusicSavedRepository extends JpaRepository<MusicSavedEntity, Long> {
    MusicSavedEntity save(MusicSavedEntity musicSaved);

    Optional<MusicSavedEntity> findById(Long id);

    @Query("select ms from MusicSavedEntity ms where ms.music.id = :musicId")
    List<MusicSavedEntity> findByMusicId(@Param("musicId") Long musicId);

    @Query("select ms from MusicSavedEntity ms where ms.user.id = :userId")
    List<MusicSavedEntity> findByUserId(@Param("userId") Long userId);

    @Query("select ms from MusicSavedEntity ms where ms.user.id = :userId and ms.music.id = :musicId")
    MusicSavedEntity findByUserIdAndMusicId(@Param("userId") Long userId, @Param("musicId") Long musicId);
}
