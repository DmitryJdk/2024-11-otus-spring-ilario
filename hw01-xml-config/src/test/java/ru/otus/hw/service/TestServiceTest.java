package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestServiceTest {

    @Test
    @DisplayName("Проверка загрузки контекста")
    public void test_context() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        TestService ts = context.getBean(TestService.class);
        assertThat(ts).isNotNull();
    }

    @Test
    @DisplayName("Проверка сообщений при пустом списке")
    public void empty_list() {
        var ioService = Mockito.mock(IOService.class);
        var questionDao = Mockito.mock(QuestionDao.class);
        TestService ts = new TestServiceImpl(ioService, questionDao);
        when(questionDao.findAll()).thenReturn(List.of());
        ts.executeTest();
        verify(questionDao, times(1)).findAll();
        verify(ioService, times(1)).printLine(any());
        verify(ioService, times(1)).printFormattedLine(any());
    }

    @Test
    @DisplayName("Проверка сообщений при непустом списке")
    public void list_has_questions() {
        var ioService = Mockito.mock(IOService.class);
        var questionDao = Mockito.mock(QuestionDao.class);
        TestService ts = new TestServiceImpl(ioService, questionDao);
        var question = new Question("test", List.of());
        when(questionDao.findAll()).thenReturn(List.of(question));
        ts.executeTest();
        verify(questionDao, times(1)).findAll();
        verify(ioService, times(3)).printLine(any());
        verify(ioService, times(1)).printFormattedLine(any());
    }
}
