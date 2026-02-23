package com.studentzone.app.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentzone.app.user.dto.request.UserCreateRequestDTO;
import com.studentzone.app.user.dto.request.UserUpdateRequestDTO;
import com.studentzone.app.user.dto.response.UserInfoResponseDTO;
import com.studentzone.app.user.entity.UserEntity;
import com.studentzone.app.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserInfoResponseDTO> createUser(@Valid @RequestBody UserCreateRequestDTO request) {
        UserInfoResponseDTO created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponseDTO> getUserById(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserInfoResponseDTO> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserInfoResponseDTO> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserInfoResponseDTO>> searchUsersByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.searchByUsername(username));
    }

    @GetMapping("/{userId}/unlocked")
    public ResponseEntity<UserInfoResponseDTO> getUserWithUnlockedItems(@PathVariable Long userId) {
        return userService.findByIdWithUnlockedItems(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/saved")
    public ResponseEntity<UserInfoResponseDTO> getUserWithSavedItems(@PathVariable Long userId) {
        return userService.findByIdWithSavedItems(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<UserInfoResponseDTO>> getLeaderboard(
            @RequestParam(defaultValue = "points") String sortBy) {
        List<UserInfoResponseDTO> result = sortBy.equals("both")
                ? userService.getLeaderboardByPointsAndTimes()
                : userService.getLeaderboardByPoints();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/leaderboard/paged")
    public ResponseEntity<Page<UserEntity>> getLeaderboardPaged(Pageable pageable) {
        return ResponseEntity.ok(userService.getLeaderboardByPoints(pageable));
    }

    @GetMapping("/leaderboard/top10")
    public ResponseEntity<List<UserInfoResponseDTO>> getTop10() {
        return ResponseEntity.ok(userService.getTop10ByPoints());
    }

    @PatchMapping("/{userId}/profile")
    public ResponseEntity<Void> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequestDTO request) {
        int updated = userService.updateProfile(userId, request.getUsername(), request.getEmail(), request.getAvatarUrl());
        return updated > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long userId,
            @RequestBody Map<String, String> body) {
        String password = body.get("password");
        if (password == null || password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        int updated = userService.updatePassword(userId, password);
        return updated > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{userId}/points")
    public ResponseEntity<Void> incrementPoints(
            @PathVariable Long userId,
            @RequestParam Long amount) {
        int updated = userService.incrementTotalPoints(userId, amount);
        return updated > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{userId}/times")
    public ResponseEntity<Void> incrementTimes(
            @PathVariable Long userId,
            @RequestParam Long amount) {
        int updated = userService.incrementTotalTimes(userId, amount);
        return updated > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
