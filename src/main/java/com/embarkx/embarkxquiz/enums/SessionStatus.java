package com.embarkx.embarkxquiz.enums;

public enum SessionStatus {
    STARTED("Started"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    EXPIRED("Expired"),
    ABANDONED("Abandoned");

    private final String displayName;

    SessionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
