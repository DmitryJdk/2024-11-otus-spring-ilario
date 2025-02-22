package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.services.BookService;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookService bookService;

    private final BookConverter bookConverter;

    @ShellMethod(value = "Find all books", key = "ab")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find book by id", key = "bbid")
    public String findBookById(String id) {
        return bookService.findById(id)
                .map(bookConverter::bookToString)
                .orElse("Book with id %s not found".formatted(id));
    }

    // bins newBook Author_4 Genre_1,Genre_2,Genre_13
    @ShellMethod(value = "Insert book", key = "bins")
    public String insertBook(String title, String authorId, Set<String> genresId) {
        var savedBook = bookService.insert(title, authorId, genresId);
        return bookConverter.bookToString(savedBook);
    }

    // bupd <id> edited Author_2 Genre_9,Genre_7
    @ShellMethod(value = "Update book", key = "bupd")
    public String updateBook(String id, String title, String authorId, Set<String> genresId) {
        var savedBook = bookService.update(id, title, authorId, genresId);
        return bookConverter.bookToString(savedBook);
    }

    // bdel <id>
    @ShellMethod(value = "Delete book by id", key = "bdel")
    public void deleteBook(String id) {
        bookService.deleteById(id);
    }
}
