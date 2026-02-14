package com.embarkx.embarkxquiz.services.session;


import com.embarkx.embarkxquiz.dto.request.session.ResultResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.SessionResponseDTO;
import com.embarkx.embarkxquiz.dto.request.session.StartSessionRequestDTO;
import com.embarkx.embarkxquiz.dto.request.session.SubmitAnswerRequestDTO;
import com.embarkx.embarkxquiz.enums.SessionStatus;
import com.embarkx.embarkxquiz.exception.custom.BadRequestException;
import com.embarkx.embarkxquiz.exception.custom.ResourceNotFoundException;
import com.embarkx.embarkxquiz.exception.custom.SessionExpiredException;
import com.embarkx.embarkxquiz.mapper.SessionMapper;
import com.embarkx.embarkxquiz.models.option.Option;
import com.embarkx.embarkxquiz.models.question.Question;
import com.embarkx.embarkxquiz.models.quiz.Quiz;
import com.embarkx.embarkxquiz.models.session.Answer;
import com.embarkx.embarkxquiz.models.session.Result;
import com.embarkx.embarkxquiz.models.session.Session;
import com.embarkx.embarkxquiz.models.user.User;
import com.embarkx.embarkxquiz.repositories.option.OptionRepository;
import com.embarkx.embarkxquiz.repositories.question.QuestionRepository;
import com.embarkx.embarkxquiz.repositories.quiz.QuizRepository;
import com.embarkx.embarkxquiz.repositories.session.AnswerRepository;
import com.embarkx.embarkxquiz.repositories.session.ResultRepository;
import com.embarkx.embarkxquiz.repositories.session.SessionRepository;
import com.embarkx.embarkxquiz.repositories.user.UserRepository;
import com.embarkx.embarkxquiz.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final AnswerRepository answerRepository;
    private final ResultRepository resultRepository;
    private final SessionMapper sessionMapper;
    private final EmailService emailService;

    @Override
    public SessionResponseDTO startSession(StartSessionRequestDTO request, Long studentId) {
        log.debug("Starting session for quiz id: {} and student id: {}", request.getQuizId(), studentId);

        Quiz quiz = quizRepository.findById(Math.toIntExact(request.getQuizId()))
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + request.getQuizId()));

        if (!quiz.getIsPublished() || !quiz.getIsActive()) {
            throw new BadRequestException("Quiz is not available");
        }

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        // Check if there's an existing active session
        sessionRepository.findByStudentIdAndQuizIdAndStatus(studentId, request.getQuizId(), SessionStatus.IN_PROGRESS)
                .ifPresent(s -> {
                    throw new BadRequestException("You already have an active session for this quiz");
                });

        // Check attempt limit
        Long completedAttempts = sessionRepository.countCompletedSessionsByStudentAndQuiz(studentId, request.getQuizId());
        if (completedAttempts >= quiz.getMaxAttempts()) {
            throw new BadRequestException("Maximum attempts reached for this quiz");
        }

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(quiz.getDurationMinutes());

        Session session = Session.builder()
                .quiz(quiz)
                .student(student)
                .status(SessionStatus.IN_PROGRESS)
                .startTime(startTime)
                .endTime(endTime)
                .totalScore(quiz.getTotalMarks())
                .attemptNumber(completedAttempts.intValue() + 1)
                .build();

        Session savedSession = sessionRepository.save(session);
        log.info("Session started successfully with id: {}", savedSession.getId());

        return sessionMapper.toResponseDTO(savedSession);
    }

    @Override
    public SessionResponseDTO submitAnswer(SubmitAnswerRequestDTO request) {
        log.debug("Submitting answer for session id: {} and question id: {}", request.getSessionId(), request.getQuestionId());

        Session session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + request.getSessionId()));

        if (session.getStatus() != SessionStatus.IN_PROGRESS) {
            throw new BadRequestException("Session is not active");
        }

        if (LocalDateTime.now().isAfter(session.getEndTime())) {
            session.setStatus(SessionStatus.EXPIRED);
            sessionRepository.save(session);
            throw new SessionExpiredException("Session has expired");
        }

        Question question = questionRepository.findById(Math.toIntExact(request.getQuestionId()))
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + request.getQuestionId()));

        // Check if answer already exists
        Answer answer = answerRepository.findBySessionIdAndQuestionId(request.getSessionId(), request.getQuestionId())
                .orElse(Answer.builder()
                        .session(session)
                        .question(question)
                        .build());

        // Update answer
        if (request.getSelectedOptionId() != null) {
            Option selectedOption = optionRepository.findById(request.getSelectedOptionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Option not found"));

            answer.setSelectedOption(selectedOption);
            answer.setIsCorrect(selectedOption.getIsCorrect());
            answer.setMarksObtained(selectedOption.getIsCorrect() ? question.getMarks() : 0);
        }

        if (request.getTextAnswer() != null) {
            answer.setTextAnswer(request.getTextAnswer());
        }

        answer.setTimeSpentSeconds(request.getTimeSpentSeconds());

        answerRepository.save(answer);
        log.info("Answer submitted successfully for question id: {}", request.getQuestionId());

        return sessionMapper.toResponseDTO(session);
    }

    @Override
    public ResultResponseDTO submitSession(Long sessionId) {
        log.debug("Submitting session with id: {}", sessionId);

        Session session = sessionRepository.findByIdWithAnswers(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + sessionId));

        if (session.getStatus() != SessionStatus.IN_PROGRESS) {
            throw new BadRequestException("Session is not active");
        }

        LocalDateTime submitTime = LocalDateTime.now();
        session.setSubmitTime(submitTime);
        session.setStatus(SessionStatus.COMPLETED);

        // Calculate time taken properly
        long minutes = Duration.between(session.getStartTime(), submitTime).toMinutes();
        session.setTimeTakenMinutes((int) minutes);

        // Calculate score
        int scoreObtained = session.getAnswers().stream()
                .filter(answer -> answer.getMarksObtained() != null)
                .mapToInt(Answer::getMarksObtained)
                .sum();

        session.setScoreObtained(scoreObtained);

        double percentage = (scoreObtained * 100.0) / session.getTotalScore();
        session.setPercentage(percentage);
        session.setIsPassed(percentage >= session.getQuiz().getPassingScore());

        // Save session FIRST before creating result
        Session savedSession = sessionRepository.save(session);

        // Create result
        List<Question> allQuestions = questionRepository.findByQuizId(savedSession.getQuiz().getId());
        int totalQuestions = allQuestions.size();
        int correctAnswers = (int) savedSession.getAnswers().stream()
                .filter(a -> Boolean.TRUE.equals(a.getIsCorrect()))
                .count();
        int wrongAnswers = (int) savedSession.getAnswers().stream()
                .filter(a -> Boolean.FALSE.equals(a.getIsCorrect()))
                .count();
        int unanswered = totalQuestions - savedSession.getAnswers().size();

        // Calculate rank
        List<Result> existingResults = resultRepository.findLeaderboardByQuizId(savedSession.getQuiz().getId());
        int calculatedRank = existingResults.size() + 1;

        Result result = Result.builder()
                .session(savedSession)
                .quiz(savedSession.getQuiz())
                .student(savedSession.getStudent())
                .scoreObtained(scoreObtained)
                .totalScore(savedSession.getTotalScore())
                .percentage(percentage)
                .isPassed(savedSession.getIsPassed())
                .totalQuestions(totalQuestions)
                .correctAnswers(correctAnswers)
                .wrongAnswers(wrongAnswers)
                .unanswered(unanswered)
                .timeTakenMinutes(savedSession.getTimeTakenMinutes())
                .rank(calculatedRank)
                .feedback(generateFeedback(percentage))
                .build();

        Result savedResult = resultRepository.save(result);
        log.info("Session submitted and result calculated successfully");

        // SEND EMAIL TO STUDENT
        emailService.sendQuizResultEmail(savedResult);

        return sessionMapper.toResultResponseDTO(savedResult);
    }

    @Override
    @Transactional(readOnly = true)
    public SessionResponseDTO getSessionById(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + sessionId));

        return sessionMapper.toResponseDTO(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponseDTO> getSessionsByStudent(Long studentId) {
        return sessionRepository.findByStudentId(studentId).stream()
                .map(sessionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponseDTO> getSessionsByQuiz(Long quizId) {
        return sessionRepository.findByQuizId(quizId).stream()
                .map(sessionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SessionResponseDTO getCurrentSession(Long studentId, Long quizId) {
        Session session = sessionRepository.findByStudentIdAndQuizIdAndStatus(studentId, quizId, SessionStatus.IN_PROGRESS)
                .orElseThrow(() -> new ResourceNotFoundException("No active session found"));

        return sessionMapper.toResponseDTO(session);
    }

    private String generateFeedback(double percentage) {
        if (percentage >= 90) {
            return "Excellent! Outstanding performance!";
        } else if (percentage >= 75) {
            return "Very Good! Great job!";
        } else if (percentage >= 60) {
            return "Good! Well done!";
        } else if (percentage >= 50) {
            return "Fair! Keep practicing!";
        } else {
            return "Needs improvement. Don't give up!";
        }
    }



}
