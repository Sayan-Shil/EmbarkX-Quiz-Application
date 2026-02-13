package com.embarkx.embarkxquiz.enums;

public enum NotificationType {
    QUIZ_ASSIGNED("Quiz Assigned"),
    QUIZ_REMINDER("Quiz Reminder"),
    RESULT_PUBLISHED("Result Published"),
    ACCOUNT_CREATED("Account Created"),
    PASSWORD_RESET("Password Reset");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
