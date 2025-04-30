package ru.otus.hw.util;

import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static List<Author> authors = List.of(
            new Author(1L, "Author_1"),
            new Author(2L, "Author_2"),
            new Author(3L, "Author_3")
    );

    public static List<AuthorDto> authorsDto = authors.stream()
            .map(author -> new AuthorDto(author.getId(), author.getFullName()))
            .toList();

    public static List<Genre> genres = List.of(
            new Genre(1L, "Genre_1"),
            new Genre(2L, "Genre_2"),
            new Genre(3L, "Genre_3"),
            new Genre(4L, "Genre_4"),
            new Genre(5L, "Genre_5"),
            new Genre(6L, "Genre_6")
    );

    public static List<Book> books = List.of(
            new Book(1L, "BookTitle_1", authors.get(0),
                    List.of(genres.get(0), genres.get(1))),
            new Book(2L, "BookTitle_2", authors.get(1),
                    List.of(genres.get(2), genres.get(3))),
            new Book(3L, "BookTitle_3", authors.get(2),
                    List.of(genres.get(4), genres.get(5)))
    );

    public static List<Comment> comments = List.of(
            new Comment(1L, "Text_1", books.get(0)),
            new Comment(2L, "Text_2", books.get(0)),
            new Comment(3L, "Text_3", books.get(1)),
            new Comment(4L, "Text_4", books.get(1)),
            new Comment(5L, "Text_5", books.get(2)),
            new Comment(6L, "Text_6", books.get(2))
    );

    public static List<BookDto> bookDto = books.stream()
            .map(book -> new BookDto(
                    book.getId(),
                    book.getTitle(),
                    new AuthorDto(book.getAuthor().getId(), book.getAuthor().getFullName()),
                    book.getGenres().stream()
                            .map(Genre::getName)
                            .collect(Collectors.toSet()))
            ).collect(Collectors.toList());
}
