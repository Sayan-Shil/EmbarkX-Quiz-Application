package com.embarkx.embarkxquiz.constants;

public class SecurityConstants {
    // JWT
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "quiz-app";
    public static final String TOKEN_AUDIENCE = "quiz-app-client";

    // Authorities
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_ADMIN = "ROLE_ADMIN";
    public static final String AUTHORITY_TEACHER = "ROLE_INSTRUCTOR";
    public static final String AUTHORITY_STUDENT = "ROLE_LEARNER";

    // Public URLs
    public static final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/h2-console/**"
    };

    private SecurityConstants() {
        throw new UnsupportedOperationException("Cannot instantiate constants class");
    }
}
