package ru.otus.hw.util;

import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static List<Author> authors = List.of(
            new Author(UUID.randomUUID().toString(), "Author_1"),
            new Author(UUID.randomUUID().toString(), "Author_2"),
            new Author(UUID.randomUUID().toString(), "Author_3")
    );

    public static List<Genre> genres = List.of(
            new Genre(UUID.randomUUID().toString(), "Genre_1"),
            new Genre(UUID.randomUUID().toString(), "Genre_2"),
            new Genre(UUID.randomUUID().toString(), "Genre_3"),
            new Genre(UUID.randomUUID().toString(), "Genre_4"),
            new Genre(UUID.randomUUID().toString(), "Genre_5"),
            new Genre(UUID.randomUUID().toString(), "Genre_6")
    );

    public static List<Book> books = List.of(
            new Book(UUID.randomUUID().toString(), "Book_1", authors.get(0),
                    Set.of(genres.get(0), genres.get(1))),
            new Book(UUID.randomUUID().toString(), "Book_2", authors.get(1),
                    Set.of(genres.get(2), genres.get(3))),
            new Book(UUID.randomUUID().toString(), "Book_3", authors.get(2),
                    Set.of(genres.get(4), genres.get(5))),
            new Book(UUID.randomUUID().toString(), "Book_4", authors.get(1),
                    Set.of(genres.get(0), genres.get(1), genres.get(2))),
            new Book(UUID.randomUUID().toString(), "Book_5", authors.get(2),
                    Set.of(genres.get(3), genres.get(4), genres.get(5)))
    );

    public static List<Comment> comments = new ArrayList<>();

    static {
        books.forEach(book ->
                comments.addAll(List.of(
                                new Comment(UUID.randomUUID().toString(), "Comment_1", book),
                                new Comment(UUID.randomUUID().toString(), "Comment_2", book)
                        )
                )
        );
    }

    public static String getAuthorId(int number) {
        if (number < 0 || number >= authors.size()) {
            throw new IllegalArgumentException("Requested wrong test data");
        }
        return authors.get(number).getId();
    }

    public static Set<String> getGenresId(Integer... numbers) {
        return Arrays.stream(numbers)
                .peek(number -> {
                    if (number < 0 || number >= genres.size()) {
                        throw new IllegalArgumentException("Requested wrong test data");
                    }
                })
                .map(number -> genres.get(number).getId())
                .collect(Collectors.toSet());
    }
}
