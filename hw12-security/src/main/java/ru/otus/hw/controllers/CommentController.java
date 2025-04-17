package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentRequest;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/book/{bookId}/comment")
    public List<CommentDto> findCommentsByBookId(@PathVariable Long bookId) {
        return commentService.findByBookId(bookId);
    }

    @DeleteMapping("/api/book/{bookId}/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @PathVariable Long bookId) {
        commentService.deleteById(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/api/book/{bookId}/comment/{id}")
    public CommentDto updateComment(@PathVariable Long id, @PathVariable Long bookId,
                                    @RequestBody CommentRequest commentRequest) {
        return commentService.update(id, commentRequest.text(), bookId);
    }

    @GetMapping("/api/book/{bookId}/comment/{id}")
    public CommentDto findCommentById(@PathVariable Long id,  @PathVariable String bookId) {
        return commentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Комментарий не найдена"));
    }

    @PostMapping("/api/book/{bookId}/comment")
    public CommentDto insertComment(@PathVariable Long bookId, @RequestBody CommentRequest commentRequest) {
        return commentService.insert(commentRequest.text(), bookId);
    }
}
