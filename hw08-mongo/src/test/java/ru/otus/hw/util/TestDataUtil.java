package ru.otus.hw.util;

import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static List<Author> authors = List.of(
            new Author("Author_1"),
            new Author("Author_2"),
            new Author("Author_3")
    );

    public static List<Genre> genres = List.of(
            new Genre("Genre_1"),
            new Genre("Genre_2"),
            new Genre("Genre_3"),
            new Genre("Genre_4"),
            new Genre("Genre_5"),
            new Genre("Genre_6")
    );

    public static List<Book> books = List.of(
            new Book("Book_1", authors.get(0),
                    Set.of(genres.get(0), genres.get(1))),
            new Book("Book_2", authors.get(1),
                    Set.of(genres.get(2), genres.get(3))),
            new Book("Book_3", authors.get(2),
                    Set.of(genres.get(4), genres.get(5))),
            new Book("Book_4", authors.get(1),
                    Set.of(genres.get(0), genres.get(1), genres.get(2))),
            new Book("Book_5", authors.get(2),
                    Set.of(genres.get(3), genres.get(4), genres.get(5)))
    );

    public static List<Comment> comments = new ArrayList<>();

    static {
        books.forEach(book ->
                comments.addAll(List.of(
                                new Comment("Comment_1", book),
                                new Comment("Comment_2", book)
                        )
                )
        );
    }
}
