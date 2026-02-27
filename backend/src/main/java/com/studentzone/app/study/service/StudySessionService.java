package com.studentzone.app.study.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public StudySessionEntity startSession(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        StudySessionEntity studySession = StudySessionEntity.builder()
                .user(user)
                .startTime(LocalDateTime.now())
                .build();
        return studySessionRepository.save(studySession);
    }

    @Transactional
    public StudySessionEntity endSession(Long sessionId, Long currentUserId) {
        StudySessionEntity studySession = studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Study session not found with id: " + sessionId));

        if (studySession.getEndTime() != null) {
            return studySession; // Session already ended, return as is
        }

        if (!studySession.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Unauthorized: User cannot end this session");
        }

        studySession.setEndTime(LocalDateTime.now());

        long minutesStudied = Duration.between(studySession.getStartTime(), studySession.getEndTime()).toMinutes();

        Double pointsEarned = minutesStudied * 0.0333; // 1 point for every 30 minutes (i.e., 1/30 points per minute)

        studySession.setPointsEarned(pointsEarned);

        // update user's total points and total times
        UserEntity user = studySession.getUser();
        user.setTotalPoints(user.getTotalPoints() + pointsEarned);
        user.setTotalTimes(user.getTotalTimes() + 1);

        userRepository.save(user);
        return studySessionRepository.save(studySession);
    }
}
