package com.studentzone.app.background.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "backgrounds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BackgroundEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl;

    private Long requiredPoint;

    private boolean isActive;

    @OneToMany(mappedBy = "background", fetch = FetchType.LAZY)
    private List<BackgroundSavedEntity> backgroundSavedByUsers;

    @OneToMany(mappedBy = "background", fetch = FetchType.LAZY)
    private List<BackgroundUnlockedEntity> backgroundUnlockedByUsers;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
