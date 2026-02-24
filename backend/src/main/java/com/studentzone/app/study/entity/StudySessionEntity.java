package com.studentzone.app.study.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "study_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudySessionEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_session_id")
    private StudySessionEntity studySession;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long pointsEarned;
    private Long diffTime;
}
