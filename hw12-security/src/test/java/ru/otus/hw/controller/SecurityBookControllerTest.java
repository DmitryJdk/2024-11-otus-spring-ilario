package ru.otus.hw.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.AbstractSecurityTest;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.services.BookService;
import ru.otus.hw.util.SecurityEndpointsProvider;

import java.util.stream.Stream;

@WebMvcTest(controllers = BookController.class)
@DisplayName("Security тесты контроллера книг")
public class SecurityBookControllerTest extends AbstractSecurityTest {

    @MockitoBean
    private BookService bookService;

    @ParameterizedTest
    @MethodSource("endpoints")
    @DisplayName("Проверка доступности api книг")
    void bookEndpointsAccessibility(HttpMethod method,
                                 String url,
                                 String user,
                                 String request,
                                 Integer statusCode) throws Exception {
        checkRequest(method, url, user, request, statusCode);
    }

    public static Stream<Arguments> endpoints() throws JsonProcessingException {
        return SecurityEndpointsProvider.bookEndpoints();
    }
}
