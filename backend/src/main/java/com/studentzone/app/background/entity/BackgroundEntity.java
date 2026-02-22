package com.studentzone.app.background.entity;

import java.util.List;

import com.studentzone.app.saved.entity.BackgroundSavedEntity;
import com.studentzone.app.unlock.entity.BackgroundUnlockedEntity;

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

    @OneToMany(mappedBy = "background")
    private List<BackgroundSavedEntity> backgroundSavedByUsers;

    @OneToMany(mappedBy = "background")
    private List<BackgroundUnlockedEntity> backgroundUnlockedByUsers;
}
