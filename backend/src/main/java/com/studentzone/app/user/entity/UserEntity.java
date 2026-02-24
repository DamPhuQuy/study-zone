package com.studentzone.app.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.studentzone.app.background.entity.BackgroundSavedEntity;
import com.studentzone.app.background.entity.BackgroundUnlockedEntity;
import com.studentzone.app.music.entity.MusicSavedEntity;
import com.studentzone.app.music.entity.MusicUnlockedEntity;
import com.studentzone.app.study.entity.StudySessionEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    @Setter(lombok.AccessLevel.NONE)
    private String password;

    private Long totalPoints;

    private Long totalTimes;

    private String avatarUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<MusicSavedEntity> savedMusic;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<MusicUnlockedEntity> unlockedMusic;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<BackgroundSavedEntity> savedBackgrounds;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<BackgroundUnlockedEntity> unlockedBackgrounds;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<StudySessionEntity> studyHistory;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
