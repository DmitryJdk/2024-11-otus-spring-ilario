package ru.otus.hw.util;

import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestDataUtil {

    private TestDataUtil() {}

    public static List<Book> books = List.of(
            new Book("Book_1", new Author("Author_1"),
                    Set.of(new Genre("Genre_1"), new Genre("Genre_2"))),
            new Book("Book_2", new Author("Author_2"),
                    Set.of(new Genre("Genre_3"), new Genre("Genre_4"))),
            new Book("Book_3", new Author("Author_3"),
                    Set.of(new Genre("Genre_5"), new Genre("Genre_6")))
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
