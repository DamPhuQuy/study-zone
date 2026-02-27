package com.studentzone.app.background.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentzone.app.background.dto.response.BackgroundSavedResponseDTO;
import com.studentzone.app.background.dto.response.BackgroundUnlockedResponseDTO;
import com.studentzone.app.background.service.BackgroundService;
import com.studentzone.app.common.response.ApiResponseCommon;

@RestController
@RequestMapping("/api/v1/backgrounds")
public class BackgroundController {
    private final BackgroundService backgroundService;

    public BackgroundController(BackgroundService backgroundService) {
        this.backgroundService = backgroundService;
    }

    @GetMapping("/me/saved-backgrounds")
    public ResponseEntity<ApiResponseCommon<List<BackgroundSavedResponseDTO>>> getUserSavedBackgrounds(@AuthenticationPrincipal(expression = "id") Long userId) {
        return ResponseEntity.ok(ApiResponseCommon.success(backgroundService.getSavedBackByUserId(userId), "User saved backgrounds retrieved successfully"));
    }

    @GetMapping("/me/unlocked-backgrounds")
    public ResponseEntity<ApiResponseCommon<List<BackgroundUnlockedResponseDTO>>> getUserUnlockedBackgrounds(@AuthenticationPrincipal(expression = "id") Long userId) {
        return ResponseEntity.ok(ApiResponseCommon.success(backgroundService.getUnlockedBackByUserId(userId), "User unlocked backgrounds retrieved successfully"));
    }

    @PostMapping("/me/saved-backgrounds/{backgroundId}")
    public ResponseEntity<ApiResponseCommon<BackgroundSavedResponseDTO>> saveBackground(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long backgroundId) {
        return ResponseEntity.ok(ApiResponseCommon.success(backgroundService.saveBackground(userId, backgroundId), "Background saved successfully"));
    }

    @PostMapping("/me/unlocked-backgrounds/{backgroundId}")
    public ResponseEntity<ApiResponseCommon<BackgroundUnlockedResponseDTO>> unlockBackground(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long backgroundId) {
        return ResponseEntity.ok(ApiResponseCommon.success(backgroundService.unlockBackground(userId, backgroundId), "Background unlocked successfully"));
    }

    @DeleteMapping("/{backgroundId}")
    public ResponseEntity<Void> deleteBackground(@PathVariable Long backgroundId) {
        backgroundService.deleteBackground(backgroundId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me/saved-backgrounds/{backgroundId}")
    public ResponseEntity<Void> deleteSavedBackground(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long backgroundId) {
        backgroundService.deleteSavedBackground(userId, backgroundId);
        return ResponseEntity.noContent().build();
    }
}
