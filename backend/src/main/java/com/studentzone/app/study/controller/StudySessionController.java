package com.studentzone.app.study.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentzone.app.common.response.ApiResponseCommon;
import com.studentzone.app.study.dto.mapper.StudySessionMapper;
import com.studentzone.app.study.dto.response.StudySessionResponseDTO;
import com.studentzone.app.study.entity.StudySessionEntity;
import com.studentzone.app.study.service.StudySessionService;

@RestController
@RequestMapping("/api/v1/study")
public class StudySessionController {
    private final StudySessionService studySessionService;

    public StudySessionController(StudySessionService studySessionService) {
        this.studySessionService = studySessionService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseCommon<List<StudySessionResponseDTO>>> getAllStudySessions(@AuthenticationPrincipal(expression = "id") Long userId) {
        List<StudySessionResponseDTO> studySessions = studySessionService.getStudySessionsByUserId(userId);
        return ResponseEntity.ok(ApiResponseCommon.success(studySessions, "Study sessions retrieved successfully"));
    }

    // return object to client get id
    @PostMapping("/me/start")
    public ResponseEntity<ApiResponseCommon<StudySessionResponseDTO>> startStudy(@AuthenticationPrincipal(expression = "id") Long userId) {
        StudySessionEntity studySession = studySessionService.startSession(userId);
        return ResponseEntity.ok(ApiResponseCommon.success(StudySessionMapper.toDTO(studySession), "Study session started!"));
    }

    @PutMapping("/me/end/{sessionId}")
    public ResponseEntity<ApiResponseCommon<StudySessionResponseDTO>> endStudy(@AuthenticationPrincipal(expression = "id") Long currentUserId, @PathVariable("sessionId") Long sessionId) {
        StudySessionEntity studySession = studySessionService.endSession(sessionId, currentUserId);
        return ResponseEntity.ok(ApiResponseCommon.success(StudySessionMapper.toDTO(studySession), String.format("Study session ended. Points earned: %.1f", studySession.getPointsEarned())));
    }
}
