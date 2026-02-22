package com.studentzone.app.background.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.background.entity.BackgroundEntity;

public interface BackgroundRepository extends JpaRepository<BackgroundEntity, Long> {

}
