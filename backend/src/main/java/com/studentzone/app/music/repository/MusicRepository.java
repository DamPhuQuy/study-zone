package com.studentzone.app.music.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentzone.app.music.entity.MusicEntity;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long>{
    MusicEntity save(MusicEntity music);

    Optional<MusicEntity> findById(Long id);

    Optional<MusicEntity> findByTitle(String title);

    List<MusicEntity> findByIsActiveTrue();

    List<MusicEntity> findByIsActiveFalse();
}
