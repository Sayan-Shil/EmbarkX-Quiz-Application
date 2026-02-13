package com.embarkx.embarkxquiz.services.auth;

import com.embarkx.embarkxquiz.constants.MessageConstants;
import com.embarkx.embarkxquiz.dto.request.auth.LoginRequestDTO;
import com.embarkx.embarkxquiz.dto.request.auth.RegisterRequestDTO;
import com.embarkx.embarkxquiz.dto.response.auth.LoginResponseDTO;
import com.embarkx.embarkxquiz.dto.response.auth.UserResponseDTO;
import com.embarkx.embarkxquiz.exception.custom.BadRequestException;
import com.embarkx.embarkxquiz.exception.custom.UnauthorizedException;
import com.embarkx.embarkxquiz.mapper.UserMapper;
import com.embarkx.embarkxquiz.models.user.User;
import com.embarkx.embarkxquiz.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        log.debug("Login attempt for email: {}", loginRequest.getEmail());

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Get user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UnauthorizedException(MessageConstants.INVALID_CREDENTIALS));

        // Check if user is active
        if (!user.getIsActive()) {
            throw new UnauthorizedException(MessageConstants.ACCOUNT_DISABLED);
        }

        // Generate tokens
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        UserResponseDTO userResponse = userMapper.toResponseDTO(user);

        log.info("User logged in successfully: {}", loginRequest.getEmail());

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .user(userResponse)
                .build();
    }

    @Override
    public UserResponseDTO register(RegisterRequestDTO registerRequest) {
        log.debug("Registration attempt for email: {}", registerRequest.getEmail());
        // Check if user already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException(MessageConstants.USER_ALREADY_EXISTS);
        }

        // Create new user
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .role(registerRequest.getRole())
                .phoneNumber(registerRequest.getPhoneNumber())
                .isActive(true)
                .isEmailVerified(false)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getEmail());

        return userMapper.toResponseDTO(savedUser);

    }

    @Override
    public LoginResponseDTO refreshToken(String refreshToken) {
        log.debug("Refresh token request");

        // Extract username from refresh token
        String username = jwtService.extractUsername(refreshToken);

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Validate refresh token
        if (!jwtService.validateToken(refreshToken, userDetails)) {
            throw new UnauthorizedException(MessageConstants.TOKEN_INVALID);
        }

        // Generate new access token
        String newAccessToken = jwtService.generateToken(userDetails);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UnauthorizedException(MessageConstants.USER_NOT_FOUND));

        UserResponseDTO userResponse = userMapper.toResponseDTO(user);
        log.info("Token refreshed successfully for user: {}", username);

        return LoginResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .user(userResponse)
                .build();
    }
}
