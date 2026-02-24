package com.studentzone.app.study.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentzone.app.study.entity.StudySessionEntity;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySessionEntity, Long>{

    StudySessionEntity save(StudySessionEntity studySession);

    Optional<StudySessionEntity> findById(Long id);

    @Query("select s from StudySessionEntity s where s.user.id = :userId")
    List<StudySessionEntity> findByUserId(Long userId);
}
