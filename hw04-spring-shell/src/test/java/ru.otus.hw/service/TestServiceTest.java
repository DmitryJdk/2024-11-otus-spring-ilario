package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.util.TestDataHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false"
})
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class TestServiceTest {

    @MockBean
    private LocalizedIOService ioService;
    @MockBean
    private QuestionDao questionDao;
    @Autowired
    private TestServiceImpl testService;

    @Test
    public void zeroCorrectAnswersWhenNoQuestions() {
        var testResult = testService.executeTestFor(TestDataHelper.defaultTestStudent);
        assertThat(testResult.getRightAnswersCount()).isEqualTo(0);
        assertThat(testResult.getStudent()).isEqualTo(TestDataHelper.defaultTestStudent);
    }

    @Test
    public void countedCorrectAnswerWhenQuestionAnsweredCorrect() {
        when(questionDao.findAll()).thenReturn(List.of(TestDataHelper.defaultTestQuestion));
        when(ioService.readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(1);
        var testResult = testService.executeTestFor(TestDataHelper.defaultTestStudent);
        assertThat(testResult.getRightAnswersCount()).isEqualTo(1);
        assertThat(testResult.getAnsweredQuestions()).isEqualTo(List.of(TestDataHelper.defaultTestQuestion));
    }

    @Test
    public void remainsZeroCorrectAnswersWhenQuestionAnsweredIncorrect() {
        when(questionDao.findAll()).thenReturn(List.of(TestDataHelper.defaultTestQuestion));
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(2);
        var testResult = testService.executeTestFor(TestDataHelper.defaultTestStudent);
        assertThat(testResult.getRightAnswersCount()).isEqualTo(0);
        assertThat(testResult.getAnsweredQuestions()).isEqualTo(List.of(TestDataHelper.defaultTestQuestion));
    }

    @Test
    public void incorrectAnswerWhenStudentInputTrash() {
        when(questionDao.findAll()).thenReturn(List.of(TestDataHelper.defaultTestQuestion));
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString()))
                .thenThrow(IllegalArgumentException.class);
        var testResult = testService.executeTestFor(TestDataHelper.defaultTestStudent);
        assertThat(testResult.getRightAnswersCount()).isEqualTo(0);
        assertThat(testResult.getAnsweredQuestions()).isEqualTo(List.of(TestDataHelper.defaultTestQuestion));
    }
}