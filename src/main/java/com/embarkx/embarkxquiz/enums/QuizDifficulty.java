package com.embarkx.embarkxquiz.enums;

public enum QuizDifficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    EXPERT("Expert");

    private final String displayName;

    QuizDifficulty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
