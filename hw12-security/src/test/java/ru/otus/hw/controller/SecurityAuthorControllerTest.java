package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.AbstractSecurityTest;
import ru.otus.hw.controllers.AuthorController;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.util.SecurityEndpointsProvider;

import java.util.stream.Stream;

@WebMvcTest(controllers = AuthorController.class)
@DisplayName("Security тесты контроллера авторов")
public class SecurityAuthorControllerTest extends AbstractSecurityTest {

    @MockitoBean
    private AuthorService authorService;

    @ParameterizedTest
    @MethodSource("endpoints")
    @DisplayName("Проверка доступности api авторов")
    void authorEndpointsAccessibility(HttpMethod method,
                                      String url,
                                      String user,
                                      String request,
                                      Integer statusCode) throws Exception {
        checkRequest(method, url, user, request, statusCode);
    }

    public static Stream<Arguments> endpoints() {
        return SecurityEndpointsProvider.authorEndpoints();
    }
}
