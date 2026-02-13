package com.embarkx.embarkxquiz.enums;

public enum UserRole {
    ADMIN("Admin"),
    INSTRUCTOR("Instructor"),
    LEARNER("Learner");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
