package com.studentzone.app.music.entity;

import java.time.LocalDateTime;
import java.util.List;

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
@Table(name = "music")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String musicUrl;

    private Long duration;

    private Long requiredPoint;

    private boolean isActive;

    @OneToMany(mappedBy = "music", fetch = FetchType.LAZY)
    List<MusicSavedEntity> musicSavedByUsers;

    @OneToMany(mappedBy = "music", fetch = FetchType.LAZY)
    List<MusicUnlockedEntity> musicUnlockedByUsers;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
