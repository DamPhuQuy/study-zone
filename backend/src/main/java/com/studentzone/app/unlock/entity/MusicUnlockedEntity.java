package com.studentzone.app.unlock.entity;

import java.time.LocalDateTime;

import com.studentzone.app.music.entity.MusicEntity;
import com.studentzone.app.user.entity.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "music_unlocked")
@Getter
@Setter
public class MusicUnlockedEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private MusicEntity music;

    Long pointsUsed;

    private LocalDateTime unlockedAt;
}
