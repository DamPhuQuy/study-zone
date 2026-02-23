package com.studentzone.app.background.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.background.entity.BackgroundSavedEntity;

public interface BackgroundSavedRepository extends JpaRepository<BackgroundSavedEntity, Long> {

}
