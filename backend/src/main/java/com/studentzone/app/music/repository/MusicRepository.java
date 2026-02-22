package com.studentzone.app.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.music.entity.MusicEntity;

public interface MusicRepository extends JpaRepository<MusicEntity, Long> {

}
