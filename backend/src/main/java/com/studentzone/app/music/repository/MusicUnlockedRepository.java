package com.studentzone.app.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.music.entity.MusicUnlockedEntity;

public interface MusicUnlockedRepository extends JpaRepository<MusicUnlockedEntity, Long> {
    
}
