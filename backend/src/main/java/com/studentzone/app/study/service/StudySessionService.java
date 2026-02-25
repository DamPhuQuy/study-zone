package com.studentzone.app.study.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.studentzone.app.study.dto.request.StudyEndSessionRequestDTO;
import com.studentzone.app.study.dto.request.StudySessionCreationRequestDTO;
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

    public StudySessionEntity startSession(StudySessionCreationRequestDTO request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        StudySessionEntity studySession = StudySessionEntity.builder()
                .user(user)
                .startTime(LocalDateTime.now())
                .build();
        return studySessionRepository.save(studySession);
    }

    public StudySessionEntity endSession(StudyEndSessionRequestDTO request) {
        StudySessionEntity studySession = studySessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Study session not found with id: " + request.getSessionId()));

        studySession.setEndTime(LocalDateTime.now());

        // Points earned formula: 1 point for every 30 minutes of study time
        long minutesStudied = Duration.between(studySession.getStartTime(), studySession.getEndTime()).toMinutes();

        long pointsEarned = minutesStudied / 30;

        studySession.setPointsEarned(pointsEarned);

        return studySessionRepository.save(studySession);
    }
}
