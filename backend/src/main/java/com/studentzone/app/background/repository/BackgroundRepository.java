package com.studentzone.app.background.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.background.entity.BackgroundEntity;

public interface BackgroundRepository extends JpaRepository<BackgroundEntity, Long> {
    BackgroundEntity save(BackgroundEntity background);

    Optional<BackgroundEntity> findById(Long id);

    List<BackgroundEntity> findByName(String name);
}
