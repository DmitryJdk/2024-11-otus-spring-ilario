package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.AbstractSecurityTest;
import ru.otus.hw.controllers.GenreController;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.util.SecurityEndpointsProvider;

import java.util.stream.Stream;

@WebMvcTest(controllers = GenreController.class)
@DisplayName("Security тесты контроллера жанров")
public class SecurityGenreControllerTest extends AbstractSecurityTest {

    @MockitoBean
    private GenreService authorService;

    @ParameterizedTest
    @MethodSource("endpoints")
    @DisplayName("Проверка доступности api жанров")
    void genreEndpointsAccessibility(HttpMethod method,
                                     String url,
                                     String user,
                                     String request,
                                     Integer statusCode,
                                     String redirectTo) throws Exception {
        checkRequest(method, url, user, request, statusCode, redirectTo);
    }

    public static Stream<Arguments> endpoints() {
        return SecurityEndpointsProvider.genreEndpoints();
    }
}
