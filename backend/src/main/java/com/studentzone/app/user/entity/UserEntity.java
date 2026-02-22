package com.studentzone.app.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.studentzone.app.saved.entity.MusicSavedEntity;
import com.studentzone.app.unlock.entity.MusicUnlockedEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
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

    @OneToMany(mappedBy = "user")
    private List<MusicSavedEntity> savedMusic;

    @OneToMany(mappedBy = "user")
    private List<MusicUnlockedEntity> unlockedMusic;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
