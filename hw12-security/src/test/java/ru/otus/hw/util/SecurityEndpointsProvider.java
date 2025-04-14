package ru.otus.hw.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.HttpMethod;
import ru.otus.hw.dto.BookRequest;
import ru.otus.hw.dto.CommentRequest;

import java.util.Set;
import java.util.stream.Stream;

@UtilityClass
public class SecurityEndpointsProvider {

    public static Stream<Arguments> authorEndpoints() {
        return Stream.of(
                Arguments.of(HttpMethod.GET, "/api/author", null, null, 302),
                Arguments.of(HttpMethod.GET, "/api/author", "user", null, 200)
        );
    }

    public static Stream<Arguments> bookEndpoints() throws JsonProcessingException {
        BookRequest bookRequest = new BookRequest("title", 1L, Set.of(1L));
        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(bookRequest);
        return Stream.of(
                Arguments.of(HttpMethod.GET, "/api/book", null, null, 302),
                Arguments.of(HttpMethod.POST, "/api/book", null, null, 302),
                Arguments.of(HttpMethod.PUT, "/api/book", null, null, 302),
                Arguments.of(HttpMethod.DELETE, "/api/book", null, null, 302),

                Arguments.of(HttpMethod.GET, "/api/book", "user", null, 200),
                Arguments.of(HttpMethod.POST, "/api/book", "user", request, 200),
                Arguments.of(HttpMethod.PUT, "/api/book/1", "user", request, 200),
                Arguments.of(HttpMethod.DELETE, "/api/book/1", "user", null, 200)
        );
    }

    public static Stream<Arguments> commentEndpoints() throws JsonProcessingException {
        CommentRequest commentRequest = new CommentRequest("comment");
        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(commentRequest);
        return Stream.of(
                Arguments.of(HttpMethod.GET, "/api/book/1/comment", null, null, 302),
                Arguments.of(HttpMethod.GET, "/api/book/1/comment/1", null, null, 302),
                Arguments.of(HttpMethod.POST, "/api/book/1/comment", null, null, 302),
                Arguments.of(HttpMethod.PUT, "/api/book/1/comment", null, null, 302),
                Arguments.of(HttpMethod.DELETE, "/api/book/1/comment", null, null, 302),

                Arguments.of(HttpMethod.GET, "/api/book/1/comment", "user", null, 200),
                Arguments.of(HttpMethod.GET, "/api/book/1/comment/1", "user", null, 200),
                Arguments.of(HttpMethod.POST, "/api/book/1/comment", "user", request, 200),
                Arguments.of(HttpMethod.PUT, "/api/book/1/comment/1", "user", request, 200),
                Arguments.of(HttpMethod.DELETE, "/api/book/1/comment/1", "user", null, 200)
        );
    }

    public static Stream<Arguments> genreEndpoints() {
        return Stream.of(
                Arguments.of(HttpMethod.GET, "/api/genre", null, null, 302),
                Arguments.of(HttpMethod.GET, "/api/genre", "user", null, 200)
        );
    }
}
