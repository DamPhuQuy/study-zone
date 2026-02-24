package com.studentzone.app.music.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studentzone.app.music.entity.MusicUnlockedEntity;

public interface MusicUnlockedRepository extends JpaRepository<MusicUnlockedEntity, Long> {
    Optional<MusicUnlockedEntity> findById(Long id);

    @Query("select mu from MusicUnlockedEntity mu where mu.user.id = :userId")
    List<MusicUnlockedEntity> findByUserId(@Param("userId") Long userId);

    @Query("select mu from MusicUnlockedEntity mu where mu.music.id = :musicId")
    List<MusicUnlockedEntity> findByMusicId(@Param("musicId") Long musicId);

    @Query("select mu from MusicUnlockedEntity mu where mu.user.id = :userId and mu.music.id = :musicId")
    MusicUnlockedEntity findByUserIdAndMusicId(@Param("userId") Long userId, @Param("musicId") Long musicId);
}
