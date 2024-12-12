package ru.otus.hw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class CsvQuestionDaoTest extends AbstractTest {

    private TestFileNameProvider testFileNameProvider;
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    public void setUp() {
        testFileNameProvider = Mockito.mock(TestFileNameProvider.class);
        csvQuestionDao = new CsvQuestionDao(testFileNameProvider);
    }

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
        assertThat(question).isEqualTo(defaultTestQuestion);
    }
}