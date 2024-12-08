package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

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
        QuestionConverter questionConverter = Mockito.mock(QuestionConverter.class);
        when(questionConverter.convert(any())).thenReturn("question with answers");
        testService = new TestServiceImpl(ioService, questionDao, questionConverter);
    }

    @Test
    public void only_info_in_output_when_no_question() {
        when(questionDao.findAll()).thenReturn(List.of());
        testService.executeTest();
        verify(questionDao, times(1)).findAll();
        verify(ioService, times(1)).printLine(any());
    }

    @Test
    public void ioService_called_when_exist_question() {
        var question = new Question("test", List.of());
        when(questionDao.findAll()).thenReturn(List.of(question));
        testService.executeTest();
        verify(questionDao, times(1)).findAll();
        verify(ioService, times(2)).printLine(any());
        verify(ioService, times(1)).printFormattedLine(any());
    }
}
