package com.embarkx.embarkxquiz.services.quiz;

import com.embarkx.embarkxquiz.dto.request.quiz.CreateQuizRequestDTO;
import com.embarkx.embarkxquiz.dto.request.quiz.UpdateQuizRequestDTO;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizDetailResponseDTO;
import com.embarkx.embarkxquiz.dto.response.quiz.QuizResponseDTO;
import com.embarkx.embarkxquiz.enums.QuizDifficulty;
import com.embarkx.embarkxquiz.exception.custom.ResourceNotFoundException;
import com.embarkx.embarkxquiz.mapper.QuestionMapper;
import com.embarkx.embarkxquiz.mapper.QuizMapper;
import com.embarkx.embarkxquiz.models.quiz.Quiz;
import com.embarkx.embarkxquiz.models.user.User;
import com.embarkx.embarkxquiz.repositories.quiz.QuizRepository;
import com.embarkx.embarkxquiz.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QuizServiceImpl implements QuizService{
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final UserRepository userRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuizResponseDTO createQuiz(CreateQuizRequestDTO request, Long userId) {
        log.debug("Creating quiz with title: {}", request.getTitle());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Quiz quiz = Quiz.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .difficulty(request.getDifficulty())
                .durationMinutes(request.getDurationMinutes())
                .passingScore(request.getPassingScore())
                .totalMarks(request.getTotalMarks())
                .category(request.getCategory())
                .instructions(request.getInstructions())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .maxAttempts(request.getMaxAttempts())
                .shuffleQuestions(request.getShuffleQuestions())
                .showResultsImmediately(request.getShowResultsImmediately())
                .isActive(true)
                .isPublished(false)
                .createdByUser(user)
                .build();

        Quiz savedQuiz = quizRepository.save(quiz);
        log.info("Quiz created successfully with id: {}", savedQuiz.getId());

        return quizMapper.toResponseDTO(savedQuiz);
    }

    @Override
    public QuizResponseDTO updateQuiz(Long quizId, UpdateQuizRequestDTO request) {
        log.debug("Updating quiz with id: {}", quizId);

        Quiz quiz = quizRepository.findById(Math.toIntExact(quizId))
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        if (request.getTitle() != null) quiz.setTitle(request.getTitle());
        if (request.getDescription() != null) quiz.setDescription(request.getDescription());
        if (request.getDifficulty() != null) quiz.setDifficulty(request.getDifficulty());
        if (request.getDurationMinutes() != null) quiz.setDurationMinutes(request.getDurationMinutes());
        if (request.getPassingScore() != null) quiz.setPassingScore(request.getPassingScore());
        if (request.getTotalMarks() != null) quiz.setTotalMarks(request.getTotalMarks());
        if (request.getCategory() != null) quiz.setCategory(request.getCategory());
        if (request.getInstructions() != null) quiz.setInstructions(request.getInstructions());
        if (request.getStartTime() != null) quiz.setStartTime(request.getStartTime());
        if (request.getEndTime() != null) quiz.setEndTime(request.getEndTime());
        if (request.getMaxAttempts() != null) quiz.setMaxAttempts(request.getMaxAttempts());
        if (request.getShuffleQuestions() != null) quiz.setShuffleQuestions(request.getShuffleQuestions());
        if (request.getShowResultsImmediately() != null) quiz.setShowResultsImmediately(request.getShowResultsImmediately());
        if (request.getIsActive() != null) quiz.setIsActive(request.getIsActive());
        if (request.getIsPublished() != null) quiz.setIsPublished(request.getIsPublished());

        Quiz updatedQuiz = quizRepository.save(quiz);
        log.info("Quiz updated successfully with id: {}", quizId);

        return quizMapper.toResponseDTO(updatedQuiz);
    }

    @Override
    public void deleteQuiz(Long quizId) {
        log.debug("Deleting quiz with id: {}", quizId);

        if (!quizRepository.existsById(Math.toIntExact(quizId))) {
            throw new ResourceNotFoundException("Quiz not found with id: " + quizId);
        }

        quizRepository.deleteById(Math.toIntExact(quizId));
        log.info("Quiz deleted successfully with id: {}", quizId);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizResponseDTO getQuizById(Long quizId) {
        log.debug("Fetching quiz with id: {}", quizId);

        Quiz quiz = quizRepository.findById(Math.toIntExact(quizId))
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        return quizMapper.toResponseDTO(quiz);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizDetailResponseDTO getQuizWithQuestions(Long quizId) {
        log.debug("Fetching quiz with questions for id: {}", quizId);

        Quiz quiz = quizRepository.findByIdWithQuestions(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        return quizMapper.toDetailResponseDTO(quiz, questionMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDTO> getAllQuizzes() {
        log.debug("Fetching all quizzes");

        return quizRepository.findAll().stream()
                .map(quizMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDTO> getPublishedQuizzes() {
        log.debug("Fetching published quizzes");

        return quizRepository.findAllPublishedAndActive().stream()
                .map(quizMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDTO> getQuizzesByDifficulty(QuizDifficulty difficulty) {
        log.debug("Fetching quizzes by difficulty: {}", difficulty);

        return quizRepository.findByDifficulty(difficulty).stream()
                .map(quizMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDTO> getQuizzesByCategory(String category) {
        log.debug("Fetching quizzes by category: {}", category);

        return quizRepository.findByCategory(category).stream()
                .map(quizMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDTO> getQuizzesByCreator(Long userId) {
        log.debug("Fetching quizzes by creator id: {}", userId);

        return quizRepository.findByCreatedByUserId(userId).stream()
                .map(quizMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDTO> searchQuizzes(String keyword) {
        log.debug("Searching quizzes with keyword: {}", keyword);

        return quizRepository.searchByTitle(keyword).stream()
                .map(quizMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuizResponseDTO publishQuiz(Long quizId) {
        log.debug("Publishing quiz with id: {}", quizId);

        Quiz quiz = quizRepository.findById(Math.toIntExact(quizId))
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        quiz.setIsPublished(true);
        Quiz publishedQuiz = quizRepository.save(quiz);

        log.info("Quiz published successfully with id: {}", quizId);
        return quizMapper.toResponseDTO(publishedQuiz);
    }

    @Override
    public QuizResponseDTO unpublishQuiz(Long quizId) {
        log.debug("Unpublishing quiz with id: {}", quizId);

        Quiz quiz = quizRepository.findById(Math.toIntExact(quizId))
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        quiz.setIsPublished(false);
        Quiz unpublishedQuiz = quizRepository.save(quiz);

        log.info("Quiz unpublished successfully with id: {}", quizId);
        return quizMapper.toResponseDTO(unpublishedQuiz);
    }

    @Override
    public QuizResponseDTO activateQuiz(Long quizId) {
        log.debug("Activating quiz with id: {}", quizId);

        Quiz quiz = quizRepository.findById(Math.toIntExact(quizId))
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        quiz.setIsActive(true);
        Quiz activatedQuiz = quizRepository.save(quiz);

        log.info("Quiz activated successfully with id: {}", quizId);
        return quizMapper.toResponseDTO(activatedQuiz);
    }

    @Override
    public QuizResponseDTO deactivateQuiz(Long quizId) {
        log.debug("Deactivating quiz with id: {}", quizId);

        Quiz quiz = quizRepository.findById(Math.toIntExact(quizId))
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        quiz.setIsActive(false);
        Quiz deactivatedQuiz = quizRepository.save(quiz);

        log.info("Quiz deactivated successfully with id: {}", quizId);
        return quizMapper.toResponseDTO(deactivatedQuiz);
    }
}
