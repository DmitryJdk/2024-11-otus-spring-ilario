package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {

    /**
     * WARNING NOTE: not all actions used on UI
     */

    private final CommentService commentService;

    @GetMapping(value = "/book/{bookId}/comment", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<CommentDto> findCommentsByBookId(@PathVariable String bookId) {
        return commentService.findByBookId(bookId);
    }

    @DeleteMapping(value = "/book/{bookId}/comment/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ResponseEntity<?>> deleteComment(@PathVariable String id, @PathVariable String bookId) {
        return commentService.deleteById(id)
                .then(Mono.just(new ResponseEntity<>("{}", HttpStatus.OK)));
    }

    @PostMapping(value = "/book/{bookId}/comment/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<CommentDto> updateComment(@PathVariable String id, @PathVariable String bookId, String text) {
        return commentService.update(id, text, bookId);
    }

    @GetMapping(value = "/book/{bookId}/comment/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<CommentDto> findCommentById(@PathVariable String id,  @PathVariable String bookId) {
        return commentService.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Comment not found")));
    }

    @PostMapping(value = "/book/{bookId}/comment/add", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<CommentDto> insertComment(@PathVariable String bookId, String text) {
        return commentService.insert(text, bookId);
    }
}
