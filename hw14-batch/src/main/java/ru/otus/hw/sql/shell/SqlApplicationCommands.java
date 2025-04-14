package ru.otus.hw.sql.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.sql.dto.BookDto;
import ru.otus.hw.sql.service.SqlBookService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class SqlApplicationCommands {

    private final SqlBookService sqlBookService;

    @ShellMethod(value = "get postgres current state", key = {"g", "get"})
    public List<BookDto> getCurrentState() {
        return sqlBookService.getBooks();
    }
}
