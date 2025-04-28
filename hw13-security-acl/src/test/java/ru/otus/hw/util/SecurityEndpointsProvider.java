package ru.otus.hw.util;

import lombok.experimental.UtilityClass;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.HttpMethod;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.stream.Stream;

@UtilityClass
public class SecurityEndpointsProvider {

    public static Stream<Arguments> authorEndpoints() {
        var allAuthors = TestDataUtil.authorsDto;
        var exceptFirstAuthor = allAuthors.stream()
                .filter(author -> !author.getId().equals(1L))
                .toList();
        return Stream.of(
                Arguments.of(HttpMethod.GET, "/author", null, null, 302, null, null, null, null),
                Arguments.of(HttpMethod.GET, "/author", "user", null, 200, null, "authors", "authors",
                        allAuthors),
                Arguments.of(HttpMethod.GET, "/author", "user1", null, 200, null, "authors", "authors",
                        exceptFirstAuthor)
        );
    }

    public static Stream<Arguments> bookEndpoints() {
        Book book = TestDataUtil.books.get(0);
        String request = "title=%s&authorId=%d&genresIds=%d&genresIds=%d"
                .formatted(book.getTitle(),
                        book.getAuthor().getId(),
                        book.getGenres().get(0).getId(),
                        book.getGenres().get(0).getId()
                );
        List<BookDto> allBooks = TestDataUtil.bookDto;
        List<BookDto> exceptBooksByFirstAuthor = allBooks.stream()
                .filter(bookDto -> !bookDto.author().getId().equals(1L))
                .toList();
        return Stream.of(
                Arguments.of(HttpMethod.GET, "/book", null, null, 302, null, null, null, null),
                Arguments.of(HttpMethod.GET, "/book/1", null, null, 302, null, null, null, null),
                Arguments.of(HttpMethod.POST, "/book/1", null, null, 302, null, null, null, null),
                Arguments.of(HttpMethod.DELETE, "/book/2", null, null, 302, null, null, null, null),
                Arguments.of(HttpMethod.GET, "/book/add", null, null, 302, null, null, null, null),
                Arguments.of(HttpMethod.PUT, "/book/add", null, null, 302, null, null, null, null),

                Arguments.of(HttpMethod.GET, "/book", "user", null, 200, null, "books", "books", allBooks),
                Arguments.of(HttpMethod.GET, "/book", "user1", null, 200, null, "books", "books",
                        exceptBooksByFirstAuthor),
                Arguments.of(HttpMethod.GET, "/book/1", "user", null, 200, null, null, null, null),
                Arguments.of(HttpMethod.POST, "/book/1", "user", request, 302, "/book", null, null, null),
                Arguments.of(HttpMethod.DELETE, "/book/2", "user", null, 302, "/book", null, null, null),
                Arguments.of(HttpMethod.GET, "/book/add", "user", null, 200, null, null, null, null),
                Arguments.of(HttpMethod.PUT, "/book/add", "user", request, 302, "/book", null, null, null)

        );
    }

    public static Stream<Arguments> commentEndpoints() {
        Comment comment = TestDataUtil.comments.get(0);
        var request = "text=%s".formatted(comment.getText());
        return Stream.of(
                Arguments.of(HttpMethod.GET, "/book/1/comment", null, null, 302, null),
                Arguments.of(HttpMethod.DELETE, "/book/1/comment/2", null, null, 302, null),
                Arguments.of(HttpMethod.POST, "/book/1/comment/1", null, null, 302, null),
                Arguments.of(HttpMethod.GET, "/book/1/comment/1", null, null, 302, null),
                Arguments.of(HttpMethod.GET, "/book/1/comment/add", null, null, 302, null),
                Arguments.of(HttpMethod.PUT, "/book/1/comment/add", null, null, 302, null),

                Arguments.of(HttpMethod.GET, "/book/1/comment", "user", null, 200, null),
                Arguments.of(HttpMethod.DELETE, "/book/1/comment/2", "user", null, 302, "/book/1/comment"),
                Arguments.of(HttpMethod.POST, "/book/1/comment/1", "user", request, 302, "/book/1/comment"),
                Arguments.of(HttpMethod.GET, "/book/1/comment/1", "user", null, 200, null),
                Arguments.of(HttpMethod.GET, "/book/1/comment/add", "user", null, 200, null),
                Arguments.of(HttpMethod.PUT, "/book/1/comment/add", "user", request, 302, "/book/1/comment")
        );
    }

    public static Stream<Arguments> genreEndpoints() {
        return Stream.of(
                Arguments.of(HttpMethod.GET, "/genre", null, null, 302, null),
                Arguments.of(HttpMethod.GET, "/genre", "user", null, 200, null)
        );
    }
}
