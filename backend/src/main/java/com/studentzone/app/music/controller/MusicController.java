package com.studentzone.app.music.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentzone.app.common.response.ApiResponseCommon;
import com.studentzone.app.music.dto.response.MusicSavedResponseDTO;
import com.studentzone.app.music.dto.response.MusicUnlockedResponseDTO;
import com.studentzone.app.music.service.MusicService;

@RestController
@RequestMapping("/api/v1/music")
public class MusicController {
    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/me/saved-music")
    public ResponseEntity<ApiResponseCommon<List<MusicSavedResponseDTO>>> getUserSavedMusic(@AuthenticationPrincipal(expression = "id") Long userId) {
        return ResponseEntity.ok(ApiResponseCommon.success(musicService.getSavedMusicByUserId(userId), "User saved music retrieved successfully"));
    }

    @GetMapping("/me/unlocked-music")
    public ResponseEntity<ApiResponseCommon<List<MusicUnlockedResponseDTO>>> getUserUnlockedMusic(@AuthenticationPrincipal(expression = "id") Long userId) {
        return ResponseEntity.ok(ApiResponseCommon.success(musicService.getUnlockedMusicByUserId(userId), "User unlocked music retrieved successfully"));
    }

    @PostMapping("/me/saved-music/{musicId}")
    public ResponseEntity<ApiResponseCommon<MusicSavedResponseDTO>> saveMusic(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long musicId) {
        return ResponseEntity.ok(ApiResponseCommon.success(musicService.saveMusic(userId, musicId), "Music saved successfully"));
    }

    @PostMapping("/me/unlocked-music/{musicId}")
    public ResponseEntity<ApiResponseCommon<MusicUnlockedResponseDTO>> unlockMusic(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long musicId) {
        return ResponseEntity.ok(ApiResponseCommon.success(musicService.unlockMusic(userId, musicId), "Music unlocked successfully"));
    }

    // only admin
    @DeleteMapping("/{musicId}")
    public ResponseEntity<Void> deleteMusic(@PathVariable Long musicId) {
        musicService.deleteMusic(musicId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me/saved/{musicId}")
    public ResponseEntity<Void> deleteSavedMusic(@AuthenticationPrincipal(expression = "id") Long userId, @PathVariable Long musicId) {
        musicService.deleteSavedMusic(userId, musicId);
        return ResponseEntity.noContent().build();
    }
}
