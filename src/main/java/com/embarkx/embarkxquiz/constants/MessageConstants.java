package com.embarkx.embarkxquiz.constants;

public class MessageConstants {
    // User Messages
    public static final String USER_FETCHED_SUCCESS = "User fetched successfully";
    public static final String USERS_FETCHED_SUCCESS = "Users fetched successfully";
    public static final String USER_UPDATED_SUCCESS = "User updated successfully";
    public static final String USER_DELETED_SUCCESS = "User deleted successfully";
    public static final String USER_ACTIVATED_SUCCESS = "User activated successfully";
    public static final String USER_DEACTIVATED_SUCCESS = "User deactivated successfully";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ALREADY_EXISTS = "User already exists with this email";

    // Auth Messages
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String LOGOUT_SUCCESS = "Logout successful";
    public static final String REGISTRATION_SUCCESS = "Registration successful";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String ACCOUNT_DISABLED = "Account is disabled";
    public static final String TOKEN_INVALID = "Invalid token";
    public static final String TOKEN_EXPIRED = "Token has expired";

    // Quiz Messages
    public static final String QUIZ_CREATED_SUCCESS = "Quiz created successfully";
    public static final String QUIZ_UPDATED_SUCCESS = "Quiz updated successfully";
    public static final String QUIZ_DELETED_SUCCESS = "Quiz deleted successfully";
    public static final String QUIZ_FETCHED_SUCCESS = "Quiz fetched successfully";
    public static final String QUIZZES_FETCHED_SUCCESS = "Quizzes fetched successfully";
    public static final String QUIZ_NOT_FOUND = "Quiz not found";

    // Question Messages
    public static final String QUESTION_CREATED_SUCCESS = "Question created successfully";
    public static final String QUESTION_UPDATED_SUCCESS = "Question updated successfully";
    public static final String QUESTION_DELETED_SUCCESS = "Question deleted successfully";
    public static final String QUESTION_NOT_FOUND = "Question not found";

    // Session Messages
    public static final String SESSION_STARTED_SUCCESS = "Session started successfully";
    public static final String SESSION_COMPLETED_SUCCESS = "Session completed successfully";
    public static final String SESSION_NOT_FOUND = "Session not found";
    public static final String SESSION_EXPIRED = "Session has expired";
    public static final String ANSWER_SUBMITTED_SUCCESS = "Answer submitted successfully";

    // Result Messages
    public static final String RESULT_FETCHED_SUCCESS = "Result fetched successfully";
    public static final String RESULTS_FETCHED_SUCCESS = "Results fetched successfully";

    // General Messages
    public static final String OPERATION_SUCCESS = "Operation completed successfully";
    public static final String OPERATION_FAILED = "Operation failed";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
    public static final String FORBIDDEN_ACCESS = "Access forbidden";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error occurred";

    private MessageConstants() {
        throw new UnsupportedOperationException("Cannot instantiate constants class");
    }
}
