package com.embarkx.embarkxquiz.controller.auth;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.request.auth.LoginRequestDTO;
import com.embarkx.embarkxquiz.dto.response.auth.LoginResponseDTO;
import com.embarkx.embarkxquiz.dto.response.common.ApiResponse;
import com.embarkx.embarkxquiz.services.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(response, MessageConstants.LOGIN_SUCCESS));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        LoginResponseDTO response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiResponse.success(response, "Token refreshed successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        // In JWT, logout is handled on client side by removing the token
        // Optionally, we can implement token blacklisting here
        return ResponseEntity.ok(ApiResponse.success(null, MessageConstants.LOGOUT_SUCCESS));
    }


}
