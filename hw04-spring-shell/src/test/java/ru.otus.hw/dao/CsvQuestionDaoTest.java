package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.util.TestDataHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class CsvQuestionDaoTest {

    @MockBean
    private TestFileNameProvider testFileNameProvider;
    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @Configuration
    @ComponentScan("ru.otus.hw.dao")
    static class DaoTestConfiguration {}

    @Test
    public void daoThrowsExceptionWhenSourceFileNotExists() {
        when(testFileNameProvider.getTestFileName()).thenReturn("not_exists");
        assertThrows(QuestionReadException.class, () -> csvQuestionDao.findAll());
    }

    @Test
    public void readCsvFileAndCheckLoadingResult() {
        when(testFileNameProvider.getTestFileName()).thenReturn("question-test.csv");
        var loadedQuestions = csvQuestionDao.findAll();
        assertThat(loadedQuestions.size()).isEqualTo(1);
        var question = loadedQuestions.get(0);
        assertThat(question).isEqualTo(TestDataHelper.defaultTestQuestion);
    }
}