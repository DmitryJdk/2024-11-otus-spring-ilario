package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TestServiceTest {

    private IOService ioService;
    private QuestionDao questionDao;
    private TestService testService;

    @BeforeEach
    public void beforeEach() {
        ioService = Mockito.mock(IOService.class);
        questionDao = Mockito.mock(QuestionDao.class);
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    public void empty_list() {
        when(questionDao.findAll()).thenReturn(List.of());
        testService.executeTest();
        verify(questionDao, times(1)).findAll();
        verify(ioService, times(1)).printLine(any());
    }

    @Test
    public void list_has_questions() {
        var question = new Question("test", List.of());
        when(questionDao.findAll()).thenReturn(List.of(question));
        testService.executeTest();
        verify(questionDao, times(1)).findAll();
        verify(ioService, times(2)).printLine(any());
        verify(ioService, times(1)).printFormattedLine(any());
    }

    @Test
    public void loading_not_existing_file_error() {
        TestFileNameProvider fileNameProvider = Mockito.mock(TestFileNameProvider.class);
        when(fileNameProvider.getTestFileName()).thenReturn("not_existing_file.csv");
        questionDao = new CsvQuestionDao(fileNameProvider);
        testService = new TestServiceImpl(ioService, questionDao);
        assertThrows(QuestionReadException.class, () -> testService.executeTest());
    }
}
