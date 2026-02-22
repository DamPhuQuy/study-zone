package com.studentzone.app.leaderboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.leaderboard.entity.StudySessionEntity;

public interface StudySessionRepository extends JpaRepository<StudySessionEntity, Long> {

}
