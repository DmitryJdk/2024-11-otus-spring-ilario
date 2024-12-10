package ru.otus.hw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.QuestionConverter;
import ru.otus.hw.service.TestServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TestServiceTest extends AbstractTest {

    private IOService ioService;
    private QuestionDao questionDao;
    private TestServiceImpl testService;

    @BeforeEach
    public void setUp() {
        ioService = Mockito.mock(IOService.class);
        questionDao = Mockito.mock(QuestionDao.class);
        QuestionConverter questionConverter = Mockito.mock(QuestionConverter.class);
        testService = new TestServiceImpl(ioService, questionDao, questionConverter);
    }

    @Test
    public void zero_correct_answers_when_no_questions() {
        var testResult = testService.executeTestFor(defaultTestStudent);
        assertThat(testResult.getRightAnswersCount()).isEqualTo(0);
        assertThat(testResult.getStudent()).isEqualTo(defaultTestStudent);
    }

    @Test
    public void counted_correct_answer_when_question_answered_correct() {
        when(questionDao.findAll()).thenReturn(List.of(defaultTestQuestion));
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(1);
        var testResult = testService.executeTestFor(defaultTestStudent);
        assertThat(testResult.getRightAnswersCount()).isEqualTo(1);
        assertThat(testResult.getAnsweredQuestions()).isEqualTo(List.of(defaultTestQuestion));
    }

    @Test
    public void remains_zero_correct_answers_when_question_answered_incorrect() {
        when(questionDao.findAll()).thenReturn(List.of(defaultTestQuestion));
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(2);
        var testResult = testService.executeTestFor(defaultTestStudent);
        assertThat(testResult.getRightAnswersCount()).isEqualTo(0);
        assertThat(testResult.getAnsweredQuestions()).isEqualTo(List.of(defaultTestQuestion));
    }

    @Test
    public void incorrect_answer_when_student_input_trash() {
        when(questionDao.findAll()).thenReturn(List.of(defaultTestQuestion));
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString()))
                .thenThrow(IllegalArgumentException.class);
        var testResult = testService.executeTestFor(defaultTestStudent);
        assertThat(testResult.getRightAnswersCount()).isEqualTo(0);
        assertThat(testResult.getAnsweredQuestions()).isEqualTo(List.of(defaultTestQuestion));
    }
}
