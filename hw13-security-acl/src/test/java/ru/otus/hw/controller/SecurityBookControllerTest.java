package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.AbstractSecurityTest;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.util.SecurityEndpointsProvider;
import ru.otus.hw.util.TestDataUtil;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = BookController.class)
@DisplayName("Security тесты контроллера книг")
public class SecurityBookControllerTest extends AbstractSecurityTest {

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private GenreService genreService;

    @ParameterizedTest
    @MethodSource("endpoints")
    @DisplayName("Проверка доступности api книг")
    void bookEndpointsAccessibility(HttpMethod method,
                                    String url,
                                    String user,
                                    String request,
                                    Integer statusCode,
                                    String redirectTo) throws Exception {
        when(bookService.findById(1)).thenReturn(Optional.of(TestDataUtil.bookDto.get(0)));
        checkRequest(method, url, user, request, statusCode, redirectTo);
    }

    public static Stream<Arguments> endpoints() {
        return SecurityEndpointsProvider.bookEndpoints();
    }
}
