package com.studentzone.app.music.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.studentzone.app.saved.entity.MusicSavedEntity;
import com.studentzone.app.unlocked.entity.MusicUnlockedEntity;

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

    private String name;

    private String musicUrl;

    private Long duration;

    private Long requiredPoint;

    private boolean isActive;

    @OneToMany(mappedBy = "music")
    List<MusicSavedEntity> musicSavedByUsers;

    @OneToMany(mappedBy = "music")
    List<MusicUnlockedEntity> musicUnlockedByUsers;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
