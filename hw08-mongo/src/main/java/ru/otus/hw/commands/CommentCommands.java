package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(String id) {
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse("Comment with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Find comments by book id", key = "cbbid")
    public String findCommentsByBookId(String id) {
        return commentService.findByBookId(id).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // cins comm <bookId>
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String text, String bookId) {
        var savedComment = commentService.insert(text, bookId);
        return commentConverter.commentToString(savedComment);
    }

    // cupd <id> new_com <bookId>
    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(String id, String text, String bookId) {
        var savedComment = commentService.update(id, text, bookId);
        return commentConverter.commentToString(savedComment);
    }

    // cdel <id>
    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteBook(String id) {
        commentService.deleteById(id);
    }
}
