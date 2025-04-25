package ru.otus.hw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.AbstractSecurityTest;
import ru.otus.hw.controllers.CommentController;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.util.SecurityEndpointsProvider;

import java.util.Optional;
import java.util.stream.Stream;

@WebMvcTest(controllers = CommentController.class)
@DisplayName("Security тесты контроллера комментариев")
public class SecurityCommentControllerTest extends AbstractSecurityTest {

    @MockitoBean
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        Mockito.when(commentService.findById(1L))
                .thenReturn(Optional.of(new CommentDto(1L, "test")));
    }

    @ParameterizedTest
    @MethodSource("endpoints")
    @DisplayName("Проверка доступности api комментариев")
    void commentEndpointsAccessibility(HttpMethod method,
                                       String url,
                                       String user,
                                       String request,
                                       Integer statusCode,
                                       String redirectTo) throws Exception {

        checkRequest(method, url, user, request, statusCode, redirectTo);
    }

    public static Stream<Arguments> endpoints() {
        return SecurityEndpointsProvider.commentEndpoints();
    }
}
