package com.studentzone.app.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentzone.app.common.response.ApiResponseCommon;
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
    public ResponseEntity<ApiResponseCommon<UserDetailedResponseDTO>> getCurrentUserDetails(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(ApiResponseCommon.success(userService.getUserDetailsById(id), "User details retrieved successfully"));
    }

    @GetMapping("/me/points")
    public ResponseEntity<ApiResponseCommon<UserPointsResponseDTO>> getUserPoints(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(ApiResponseCommon.success(userService.getUserPointsById(id), "User points retrieved successfully"));
    }

    @GetMapping("/me/times")
    public ResponseEntity<ApiResponseCommon<UserTimesResponseDTO>> getUserTimes(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(ApiResponseCommon.success(userService.getUserTimesById(id), "User times retrieved successfully"));
    }

    @GetMapping("/me/saved-music")
    public ResponseEntity<ApiResponseCommon<UserSavedMusicResponseDTO>> getUserSavedMusic(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(ApiResponseCommon.success(userService.getUserSavedMusicResponseDTO(id), "User saved music retrieved successfully"));
    }

    @GetMapping("/me/saved-backgrounds")
    public ResponseEntity<ApiResponseCommon<UserSavedBackgroundResponseDTO>> getUserSavedBackgrounds(@AuthenticationPrincipal(expression = "id") Long id) {
        return ResponseEntity.ok(ApiResponseCommon.success(userService.getUserSavedBackgroundResponseDTO(id), "User saved backgrounds retrieved successfully"));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequestDTO request) {
        userService.createUser(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody UserUpdateRequestDTO request, @AuthenticationPrincipal(expression = "id") Long id) {
        userService.updateUserProfile(id, request);
        return ResponseEntity.noContent().build();
    }
}
