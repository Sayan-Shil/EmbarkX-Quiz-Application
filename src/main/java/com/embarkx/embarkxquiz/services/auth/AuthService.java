package com.embarkx.embarkxquiz.services.auth;

import com.embarkx.embarkxquiz.dto.request.auth.LoginRequestDTO;
import com.embarkx.embarkxquiz.dto.request.auth.RegisterRequestDTO;
import com.embarkx.embarkxquiz.dto.response.auth.LoginResponseDTO;
import com.embarkx.embarkxquiz.dto.response.auth.UserResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequest);
    UserResponseDTO register(RegisterRequestDTO registerRequest);
    LoginResponseDTO refreshToken(String refreshToken);
}
