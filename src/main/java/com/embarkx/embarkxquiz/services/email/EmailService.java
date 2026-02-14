package com.embarkx.embarkxquiz.services.email;

import com.embarkx.embarkxquiz.models.session.Result;

public interface EmailService {

    void sendQuizResultEmail(Result result);
    void sendWelcomeEmail(String toEmail, String userName);
    void sendQuizAssignedEmail(String toEmail, String studentName, String quizTitle);
}
