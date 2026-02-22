package com.studentzone.app.saved.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.saved.entity.MusicSavedEntity;

public interface MusicSavedRepository extends JpaRepository<MusicSavedEntity, Long> {

}
