package com.embarkx.embarkxquiz.services.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractUsername(String token);

    Boolean validateToken(String token, UserDetails userDetails);

    Boolean isTokenExpired(String token);
}
