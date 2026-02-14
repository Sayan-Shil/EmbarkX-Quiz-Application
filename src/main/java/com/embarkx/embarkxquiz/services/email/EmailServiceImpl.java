package com.embarkx.embarkxquiz.services.email;

import com.embarkx.embarkxquiz.models.session.Result;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    @Override
    public void sendQuizResultEmail(Result result) {
        try {
            String toEmail = result.getStudent().getEmail();
            String studentName = result.getStudent().getFirstName() + " " + result.getStudent().getLastName();
            String quizTitle = result.getQuiz().getTitle();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("🎉 Quiz Result: " + quizTitle);

            String emailContent = buildResultEmailContent(result, studentName, quizTitle);
            helper.setText(emailContent, true);

            mailSender.send(message);
            log.info("Quiz result email sent successfully to: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send quiz result email: {}", e.getMessage());
        }
    }

    @Async
    @Override
    public void sendWelcomeEmail(String toEmail, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Welcome to Quiz Application! 🎓");

            String emailContent = buildWelcomeEmailContent(userName);
            helper.setText(emailContent, true);

            mailSender.send(message);
            log.info("Welcome email sent successfully to: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send welcome email: {}", e.getMessage());
        }
    }

    @Async
    @Override
    public void sendQuizAssignedEmail(String toEmail, String studentName, String quizTitle) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("📝 New Quiz Assigned: " + quizTitle);

            String emailContent = buildQuizAssignedEmailContent(studentName, quizTitle);
            helper.setText(emailContent, true);

            mailSender.send(message);
            log.info("Quiz assigned email sent successfully to: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send quiz assigned email: {}", e.getMessage());
        }
    }

    private String buildResultEmailContent(Result result, String studentName, String quizTitle) {
        String passStatus = result.getIsPassed() ?
                "<span style='color: #28a745; font-weight: bold;'>✅ PASSED</span>" :
                "<span style='color: #dc3545; font-weight: bold;'>❌ FAILED</span>";

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                        .content { background: #f8f9fa; padding: 30px; border-radius: 0 0 10px 10px; }
                        .result-card { background: white; padding: 20px; margin: 20px 0; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
                        .score { font-size: 48px; font-weight: bold; color: #667eea; text-align: center; margin: 20px 0; }
                        .stats { display: table; width: 100%%; margin: 20px 0; }
                        .stat-row { display: table-row; }
                        .stat-label { display: table-cell; padding: 10px; font-weight: bold; border-bottom: 1px solid #e9ecef; }
                        .stat-value { display: table-cell; padding: 10px; text-align: right; border-bottom: 1px solid #e9ecef; }
                        .footer { text-align: center; margin-top: 30px; color: #6c757d; font-size: 14px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>🎓 Quiz Result</h1>
                        </div>
                        <div class="content">
                            <h2>Hi %s! 👋</h2>
                            <p>You have completed the quiz: <strong>%s</strong></p>
                            
                            <div class="result-card">
                                <h3 style="text-align: center;">Your Score</h3>
                                <div class="score">%.2f%%</div>
                                <p style="text-align: center; font-size: 20px;">%s</p>
                            </div>
                            
                            <div class="result-card">
                                <h3>📊 Detailed Statistics</h3>
                                <div class="stats">
                                    <div class="stat-row">
                                        <div class="stat-label">Score Obtained:</div>
                                        <div class="stat-value">%d / %d</div>
                                    </div>
                                    <div class="stat-row">
                                        <div class="stat-label">Percentage:</div>
                                        <div class="stat-value">%.2f%%</div>
                                    </div>
                                    <div class="stat-row">
                                        <div class="stat-label">Total Questions:</div>
                                        <div class="stat-value">%d</div>
                                    </div>
                                    <div class="stat-row">
                                        <div class="stat-label">Correct Answers:</div>
                                        <div class="stat-value" style="color: #28a745;">✓ %d</div>
                                    </div>
                                    <div class="stat-row">
                                        <div class="stat-label">Wrong Answers:</div>
                                        <div class="stat-value" style="color: #dc3545;">✗ %d</div>
                                    </div>
                                    <div class="stat-row">
                                        <div class="stat-label">Unanswered:</div>
                                        <div class="stat-value">%d</div>
                                    </div>
                                    <div class="stat-row">
                                        <div class="stat-label">Time Taken:</div>
                                        <div class="stat-value">%d minutes</div>
                                    </div>
                                    <div class="stat-row">
                                        <div class="stat-label">Your Rank:</div>
                                        <div class="stat-value">🏆 #%d</div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="result-card" style="background: #fff3cd; border-left: 4px solid #ffc107;">
                                <h3>💬 Feedback</h3>
                                <p style="font-size: 16px; margin: 0;">%s</p>
                            </div>
                            
                            <p style="text-align: center; margin-top: 30px;">
                                <a href="http://localhost:8081/api/student/results/my-results" 
                                   style="background: #667eea; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; display: inline-block;">
                                    View All Results
                                </a>
                            </p>
                        </div>
                        <div class="footer">
                            <p>Keep learning and improving! 📚</p>
                            <p>&copy; 2024 Quiz Application. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(
                studentName,
                quizTitle,
                result.getPercentage(),
                passStatus,
                result.getScoreObtained(),
                result.getTotalScore(),
                result.getPercentage(),
                result.getTotalQuestions(),
                result.getCorrectAnswers(),
                result.getWrongAnswers(),
                result.getUnanswered(),
                result.getTimeTakenMinutes(),
                result.getRank(),
                result.getFeedback()
        );
    }

    private String buildWelcomeEmailContent(String userName) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                        .content { background: #f8f9fa; padding: 30px; border-radius: 0 0 10px 10px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>🎉 Welcome to Quiz Application!</h1>
                        </div>
                        <div class="content">
                            <h2>Hi %s! 👋</h2>
                            <p>Welcome to our Quiz Application! We're excited to have you on board.</p>
                            <p>You can now:</p>
                            <ul>
                                <li>📝 Take quizzes</li>
                                <li>📊 View your results</li>
                                <li>🏆 Check leaderboards</li>
                                <li>📈 Track your progress</li>
                            </ul>
                            <p>Happy learning! 🚀</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(userName);
    }

    private String buildQuizAssignedEmailContent(String studentName, String quizTitle) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                        .content { background: #f8f9fa; padding: 30px; border-radius: 0 0 10px 10px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>📝 New Quiz Available!</h1>
                        </div>
                        <div class="content">
                            <h2>Hi %s! 👋</h2>
                            <p>A new quiz has been published: <strong>%s</strong></p>
                            <p>Login to start the quiz and test your knowledge!</p>
                            <p style="text-align: center; margin-top: 30px;">
                                <a href="http://localhost:8081/api/student/quizzes" 
                                   style="background: #667eea; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; display: inline-block;">
                                    View Quiz
                                </a>
                            </p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(studentName, quizTitle);
    }
}
