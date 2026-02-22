package com.studentzone.app.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.studentzone.app.saved.entity.BackgroundSavedEntity;
import com.studentzone.app.saved.entity.MusicSavedEntity;
import com.studentzone.app.unlock.entity.BackgroundUnlockedEntity;
import com.studentzone.app.unlock.entity.MusicUnlockedEntity;

import jakarta.persistence.Entity;
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

    @OneToMany(mappedBy = "user")
    private List<MusicSavedEntity> savedMusic;

    @OneToMany(mappedBy = "user")
    private List<MusicUnlockedEntity> unlockedMusic;

    @OneToMany(mappedBy = "user")
    private List<BackgroundSavedEntity> savedBackgrounds;

    @OneToMany(mappedBy = "user")
    private List<BackgroundUnlockedEntity> unlockedBackgrounds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
