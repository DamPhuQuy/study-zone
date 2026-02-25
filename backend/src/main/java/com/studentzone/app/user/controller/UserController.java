package com.studentzone.app.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentzone.app.user.dto.request.UserCreateRequestDTO;
import com.studentzone.app.user.dto.request.UserUpdateRequestDTO;
import com.studentzone.app.user.dto.response.UserDetailedResponseDTO;
import com.studentzone.app.user.dto.response.UserPointsResponseDTO;
import com.studentzone.app.user.dto.response.UserSavedBackgroundResponseDTO;
import com.studentzone.app.user.dto.response.UserSavedMusicResponseDTO;
import com.studentzone.app.user.dto.response.UserTimesResponseDTO;
import com.studentzone.app.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailedResponseDTO> getCurrentUserDetails(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(userService.getUserDetailsById(id));
    }

    @GetMapping("/me/points")
    public ResponseEntity<UserPointsResponseDTO> getUserPoints(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(userService.getUserPointsById(id));
    }

    @GetMapping("/me/times")
    public ResponseEntity<UserTimesResponseDTO> getUserTimes(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(userService.getUserTimesById(id));
    }

    @GetMapping("/me/saved-music")
    public ResponseEntity<UserSavedMusicResponseDTO> getUserSavedMusic(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(userService.getUserSavedMusicResponseDTO(id));
    }

    @GetMapping("/me/saved-backgrounds")
    public ResponseEntity<UserSavedBackgroundResponseDTO> getUserSavedBackgrounds(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(userService.getUserSavedBackgroundResponseDTO(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequestDTO request) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody UserUpdateRequestDTO request, @AuthenticationPrincipal(expression = "id") Long id) {
        userService.updateUserProfile(id, request);
        return ResponseEntity.ok().build();
    }
}
