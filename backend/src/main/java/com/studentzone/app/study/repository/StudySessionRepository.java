package com.studentzone.app.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentzone.app.study.entity.StudySessionEntity;

public interface StudySessionRepository extends JpaRepository<StudySessionEntity, Long> {

}
