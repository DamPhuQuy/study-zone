package com.studentzone.app.study.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentzone.app.study.dto.response.StudySessionResponseDTO;
import com.studentzone.app.study.service.StudySessionService;

@RestController
@RequestMapping("/api/v1/study-sessions")
public class StudySessionController {
    private final StudySessionService studySessionService;

    public StudySessionController(StudySessionService studySessionService) {
        this.studySessionService = studySessionService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<StudySessionResponseDTO>> getAllStudySessions(@AuthenticationPrincipal(expression = "id") Long userId) {
        List<StudySessionResponseDTO> studySessions = studySessionService.getStudySessionsByUserId(userId);

        return ResponseEntity.ok(studySessions);
    }

    // start and end
}
