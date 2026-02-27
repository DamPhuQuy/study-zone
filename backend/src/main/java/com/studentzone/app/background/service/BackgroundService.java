package com.studentzone.app.background.service;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentzone.app.background.dto.mapper.BackgroundMapper;
import com.studentzone.app.background.dto.response.BackgroundSavedResponseDTO;
import com.studentzone.app.background.dto.response.BackgroundUnlockedResponseDTO;
import com.studentzone.app.background.entity.BackgroundEntity;
import com.studentzone.app.background.entity.BackgroundSavedEntity;
import com.studentzone.app.background.entity.BackgroundUnlockedEntity;
import com.studentzone.app.background.repository.BackgroundRepository;
import com.studentzone.app.background.repository.BackgroundSavedRepository;
import com.studentzone.app.background.repository.BackgroundUnlockedRepository;
import com.studentzone.app.user.entity.UserEntity;
import com.studentzone.app.user.repository.UserRepository;

@Service
public class BackgroundService {
    private final UserRepository userRepository;
    private final BackgroundRepository backgroundRepository;
    private final BackgroundSavedRepository backgroundSavedRepository;
    private final BackgroundUnlockedRepository backgroundUnlockedRepository;

    public BackgroundService(UserRepository userRepository,           BackgroundRepository backgroundRepository,
                             BackgroundSavedRepository backgroundSavedRepository,
                             BackgroundUnlockedRepository backgroundUnlockedRepository) {
        this.backgroundRepository = backgroundRepository;
        this.backgroundSavedRepository = backgroundSavedRepository;
        this.backgroundUnlockedRepository = backgroundUnlockedRepository;
        this.userRepository = userRepository;
    }

    public BackgroundSavedResponseDTO getSavedBackgroundById(Long id) {
        return backgroundSavedRepository.findById(id)
                .map(saved -> BackgroundSavedResponseDTO.builder()
                        .id(saved.getId())
                        .userId(saved.getUser().getId())
                        .backgroundId(saved.getBackground().getId())
                        .savedAt(saved.getSavedAt())
                        .build())
                .orElseThrow(() -> new RuntimeException("Saved background not found with id: " + id));
    }

    public BackgroundUnlockedResponseDTO getUnlockedBackgroundById(Long id) {
        return backgroundUnlockedRepository.findById(id)
                .map(unlocked -> BackgroundMapper.toBackgroundUnlockedResponseDTO(unlocked))
                .orElseThrow(() -> new RuntimeException("Unlocked background not found with id: " + id));
    }

    @Transactional
    public BackgroundSavedResponseDTO saveBackground(@AuthenticationPrincipal(expression = "id") Long userId, Long backgroundId) {
        BackgroundEntity background = backgroundRepository.findById(backgroundId)
                .orElseThrow(() -> new RuntimeException("Background not found with id: " + backgroundId));

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        BackgroundSavedEntity backgroundSavedEntity = BackgroundSavedEntity.builder()
                .user(userRepository.getReferenceById(userId))
                .background(background)
                .build();
        return BackgroundMapper.toBackgroundSavedResponseDTO(backgroundSavedRepository.save(backgroundSavedEntity));
    }

    @Transactional
    public BackgroundUnlockedResponseDTO unlockBackground(@AuthenticationPrincipal(expression = "id") Long userId, Long backgroundId) {
        BackgroundEntity background = backgroundRepository.findById(backgroundId)
                .orElseThrow(() -> new RuntimeException("Background not found with id: " + backgroundId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // user spent points to unlock
        user.setTotalPoints(user.getTotalPoints() - background.getRequiredPoint());
        userRepository.save(user);

        BackgroundUnlockedEntity backgroundUnlockedEntity = BackgroundUnlockedEntity.builder()
                .user(user)
                .background(background)
                .build();
        return BackgroundMapper.toBackgroundUnlockedResponseDTO(backgroundUnlockedRepository.save(backgroundUnlockedEntity));
    }

    public List<BackgroundSavedResponseDTO> getSavedBackByUserId(Long userId) {
        return backgroundSavedRepository.findByUserId(userId).stream()
                                  .map(BackgroundMapper::toBackgroundSavedResponseDTO)
                                  .toList();
    }

    public List<BackgroundUnlockedResponseDTO> getUnlockedBackByUserId(Long userId) {
        return backgroundUnlockedRepository.findByUserId(userId).stream()
                                     .map(BackgroundMapper::toBackgroundUnlockedResponseDTO)
                                     .toList();
    }

    @Transactional
    public void deleteBackground(Long backgroundId) {
        BackgroundEntity background = backgroundRepository.findById(backgroundId)
                .orElseThrow(() -> new RuntimeException("Background not found with id: " + backgroundId));

        backgroundRepository.delete(background);
    }

    @Transactional
    public void deleteSavedBackground(Long userId, Long backgroundId) {
        BackgroundSavedEntity backgroundSaved = backgroundSavedRepository.findByUserIdAndBackgroundId(userId, backgroundId)
                .orElseThrow(() -> new RuntimeException("Saved background not found for userId: " + userId + " and backgroundId: " + backgroundId));

        backgroundSavedRepository.delete(backgroundSaved);
    }
}
