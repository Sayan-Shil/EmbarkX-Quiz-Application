package com.embarkx.embarkxquiz.services.question;

import com.embarkx.embarkxquiz.dto.request.question.CreateQuestionRequestDTO;
import com.embarkx.embarkxquiz.dto.request.question.UpdateQuestionRequestDTO;
import com.embarkx.embarkxquiz.dto.response.question.QuestionResponseDTO;
import com.embarkx.embarkxquiz.exception.custom.ResourceNotFoundException;
import com.embarkx.embarkxquiz.mapper.QuestionMapper;
import com.embarkx.embarkxquiz.models.option.Option;
import com.embarkx.embarkxquiz.models.question.Question;
import com.embarkx.embarkxquiz.models.quiz.Quiz;
import com.embarkx.embarkxquiz.repositories.option.OptionRepository;
import com.embarkx.embarkxquiz.repositories.question.QuestionRepository;
import com.embarkx.embarkxquiz.repositories.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final OptionRepository optionRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionResponseDTO createQuestion(CreateQuestionRequestDTO request) {
        log.debug("Creating question for quiz id: {}", request.getQuizId());

        Quiz quiz = quizRepository.findById(Math.toIntExact(request.getQuizId()))
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + request.getQuizId()));

        Question question = Question.builder()
                .quiz(quiz)
                .questionText(request.getQuestionText())
                .questionType(request.getQuestionType())
                .marks(request.getMarks())
                .orderIndex(request.getOrderIndex())
                .explanation(request.getExplanation())
                .imageUrl(request.getImageUrl())
                .isRequired(request.getIsRequired())
                .options(new ArrayList<>())
                .build();

        Question savedQuestion = questionRepository.save(question);

        // Create options if provided
        if (request.getOptions() != null && !request.getOptions().isEmpty()) {
            List<Option> options = request.getOptions().stream()
                    .map(optionDTO -> Option.builder()
                            .question(savedQuestion)
                            .optionText(optionDTO.getOptionText())
                            .isCorrect(optionDTO.getIsCorrect())
                            .orderIndex(optionDTO.getOrderIndex())
                            .build())
                    .collect(Collectors.toList());

            List<Option> savedOptions = optionRepository.saveAll(options);
            savedQuestion.setOptions(savedOptions);
        }

        log.info("Question created successfully with id: {}", savedQuestion.getId());
        return questionMapper.toResponseDTO(savedQuestion);
    }

    @Override
    public QuestionResponseDTO updateQuestion(Long questionId, UpdateQuestionRequestDTO request) {
        log.debug("Updating question with id: {}", questionId);

        Question question = questionRepository.findById(Math.toIntExact(questionId))
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

        if (request.getQuestionText() != null) question.setQuestionText(request.getQuestionText());
        if (request.getQuestionType() != null) question.setQuestionType(request.getQuestionType());
        if (request.getMarks() != null) question.setMarks(request.getMarks());
        if (request.getOrderIndex() != null) question.setOrderIndex(request.getOrderIndex());
        if (request.getExplanation() != null) question.setExplanation(request.getExplanation());
        if (request.getImageUrl() != null) question.setImageUrl(request.getImageUrl());
        if (request.getIsRequired() != null) question.setIsRequired(request.getIsRequired());

        Question updatedQuestion = questionRepository.save(question);
        log.info("Question updated successfully with id: {}", questionId);

        return questionMapper.toResponseDTO(updatedQuestion);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        log.debug("Deleting question with id: {}", questionId);

        if (!questionRepository.existsById(Math.toIntExact(questionId))) {
            throw new ResourceNotFoundException("Question not found with id: " + questionId);
        }

        questionRepository.deleteById(Math.toIntExact(questionId));
        log.info("Question deleted successfully with id: {}", questionId);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionResponseDTO getQuestionById(Long questionId) {
        log.debug("Fetching question with id: {}", questionId);

        Question question = questionRepository.findById(Math.toIntExact(questionId))
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

        return questionMapper.toResponseDTO(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> getQuestionsByQuizId(Long quizId) {
        log.debug("Fetching questions for quiz id: {}", quizId);

        if (!quizRepository.existsById(Math.toIntExact(quizId))) {
            throw new ResourceNotFoundException("Quiz not found with id: " + quizId);
        }

        List<Question> questions = questionRepository.findByQuizIdOrderByOrderIndex(quizId);

        return questions.stream()
                .map(questionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> reorderQuestions(Long quizId, List<Long> questionIds) {
        log.debug("Reordering questions for quiz id: {}", quizId);

        if (!quizRepository.existsById(Math.toIntExact(quizId))) {
            throw new ResourceNotFoundException("Quiz not found with id: " + quizId);
        }

        List<Question> questions = questionRepository.findAllById(questionIds);

        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setOrderIndex(i + 1);
        }

        List<Question> reorderedQuestions = questionRepository.saveAll(questions);

        log.info("Questions reordered successfully for quiz id: {}", quizId);

        return reorderedQuestions.stream()
                .map(questionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
