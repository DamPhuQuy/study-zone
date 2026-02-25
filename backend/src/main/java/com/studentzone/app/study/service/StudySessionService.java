package com.studentzone.app.study.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.studentzone.app.study.dto.mapper.StudySessionMapper;
import com.studentzone.app.study.dto.response.StudySessionResponseDTO;
import com.studentzone.app.study.entity.StudySessionEntity;
import com.studentzone.app.study.repository.StudySessionRepository;
import com.studentzone.app.user.entity.UserEntity;
import com.studentzone.app.user.repository.UserRepository;

@Service
public class StudySessionService {
    private final StudySessionRepository studySessionRepository;

    private final UserRepository userRepository;

    public StudySessionService(StudySessionRepository studySessionRepository, UserRepository userRepository) {
        this.studySessionRepository = studySessionRepository;
        this.userRepository = userRepository;
    }

    public List<StudySessionResponseDTO> getStudySessionsByUserId(Long userId) {
        List<StudySessionEntity> studySessions = studySessionRepository.findByUserId(userId);

        return studySessions.stream()
                .map(StudySessionMapper::toDTO)
                .toList();
    }

    public StudySessionEntity startSession(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        StudySessionEntity studySession = StudySessionEntity.builder()
                .user(user)
                .startTime(LocalDateTime.now())
                .build();
        return studySessionRepository.save(studySession);
    }

    public StudySessionEntity endSession(Long sessionId) {
        StudySessionEntity studySession = studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Study session not found with id: " + sessionId));

        studySession.setEndTime(LocalDateTime.now());

        // Points earned formula: 1 point for every 30 minutes of study time
        long minutesStudied = Duration.between(studySession.getStartTime(), studySession.getEndTime()).toMinutes();

        long pointsEarned = minutesStudied / 30;

        studySession.setPointsEarned(pointsEarned);

        return studySessionRepository.save(studySession);
    }
}
