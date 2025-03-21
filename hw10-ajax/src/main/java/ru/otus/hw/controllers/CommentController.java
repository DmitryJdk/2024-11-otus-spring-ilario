package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    /**
     * WARNING NOTE: not all actions used on UI
     */

    private final CommentService commentService;

    @GetMapping("/book/{bookId}/comment")
    public List<CommentDto> findCommentsByBookId(@PathVariable String bookId) {
        return commentService.findByBookId(bookId);
    }

    @DeleteMapping("/book/{bookId}/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String id, @PathVariable String bookId) {
        commentService.deleteById(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PostMapping("/book/{bookId}/comment/{id}")
    public CommentDto updateComment(@PathVariable String id, @PathVariable String bookId, String text) {
        return commentService.update(id, text, bookId);
    }

    @GetMapping("/book/{bookId}/comment/{id}")
    public CommentDto findCommentById(@PathVariable String id,  @PathVariable String bookId) {
        return commentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Комментарий не найдена"));
    }

    @PostMapping("/book/{bookId}/comment/add")
    public CommentDto insertComment(@PathVariable String bookId, String text) {
        return commentService.insert(text, bookId);
    }
}
