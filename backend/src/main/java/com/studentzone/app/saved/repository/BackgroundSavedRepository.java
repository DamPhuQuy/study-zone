package com.studentzone.app.saved.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.saved.entity.BackgroundSavedEntity;

public interface BackgroundSavedRepository extends JpaRepository<BackgroundSavedEntity, Long> {

}
