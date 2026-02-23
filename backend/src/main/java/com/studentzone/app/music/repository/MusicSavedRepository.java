package com.studentzone.app.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.music.entity.MusicSavedEntity;

public interface MusicSavedRepository extends JpaRepository<MusicSavedEntity, Long> {

}
