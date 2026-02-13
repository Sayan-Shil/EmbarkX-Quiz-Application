package com.embarkx.embarkxquiz.constants;

public class AppConstants {
    // Pagination
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    public static final int MAX_PAGE_SIZE = 100;

    // Quiz
    public static final int MIN_QUIZ_DURATION = 5; // minutes
    public static final int MAX_QUIZ_DURATION = 180; // minutes
    public static final int MIN_QUESTIONS_PER_QUIZ = 1;
    public static final int MAX_QUESTIONS_PER_QUIZ = 100;

    // Password
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 100;

    // Email
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Date Formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private AppConstants() {
        throw new UnsupportedOperationException("Cannot instantiate constants class");
    }
}
