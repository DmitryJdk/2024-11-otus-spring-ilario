package ru.otus.hw.nosql.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.nosql.model.NoSqlBook;
import ru.otus.hw.nosql.service.NoSqlAuthorService;
import ru.otus.hw.nosql.service.NoSqlBookService;
import ru.otus.hw.nosql.service.NoSqlGenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class NoSqlApplicationCommands {

    private final NoSqlBookService noSqlBookService;

    private final NoSqlAuthorService noSqlAuthorService;

    private final NoSqlGenreService noSqlGenreService;

    @ShellMethod(value = "get mongo current state", key = {"ch", "check"})
    public List<NoSqlBook> getBooks() {
        var authors = noSqlAuthorService.getAuthors();
        var genres = noSqlGenreService.getGenres();
        System.out.println("authors: " + authors);
        System.out.println("genres: " + genres);
        return noSqlBookService.getBooks();
    }

    @ShellMethod(value = "clear mongo", key = {"cl", "clear"})
    public String clear() {
        noSqlGenreService.clear();
        noSqlAuthorService.clear();
        noSqlBookService.clear();
        return "clear done";
    }
}
