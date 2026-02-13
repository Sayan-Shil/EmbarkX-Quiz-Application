package com.embarkx.embarkxquiz.controller.common;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.response.auth.UserResponseDTO;
import com.embarkx.embarkxquiz.dto.response.common.ApiResponse;
import com.embarkx.embarkxquiz.models.user.User;
import com.embarkx.embarkxquiz.services.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'LEARNER')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getCurrentUserProfile(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        UserResponseDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(user, MessageConstants.USER_FETCHED_SUCCESS));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'LEARNER')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateCurrentUserProfile(
            @Valid @RequestBody User updatedUser,
            Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        UserResponseDTO currentUser = userService.getUserByEmail(email);
        UserResponseDTO updated = userService.updateUser(currentUser.getId(), updatedUser);

        return ResponseEntity.ok(ApiResponse.success(updated, MessageConstants.USER_UPDATED_SUCCESS));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','INSTRUCTOR', 'LEARNER')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user, MessageConstants.USER_FETCHED_SUCCESS));
    }
}
