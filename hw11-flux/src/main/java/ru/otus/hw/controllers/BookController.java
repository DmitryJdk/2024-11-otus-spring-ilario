package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookRequest;
import ru.otus.hw.services.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "/book", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<BookDto> findAllBooks() {
        return bookService.findAll();
    }

    @GetMapping(value = "/book/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<BookDto> getBook(@PathVariable String id) {
        return bookService.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Book not found")));
    }

    @PostMapping(value = "/book/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ResponseEntity<?>> updateBook(@PathVariable String id, @RequestBody BookRequest bookRequest) {
        return bookService.update(id, bookRequest.title(), bookRequest.authorId(), bookRequest.genresIds())
                .then(Mono.just(new ResponseEntity<>("{}", HttpStatus.OK)));
    }

    @DeleteMapping(value = "/book/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ResponseEntity<?>> deleteBook(@PathVariable String id) {
        return bookService.deleteById(id)
                .then(Mono.just(new ResponseEntity<>("{}", HttpStatus.OK)));
    }

    @PostMapping(value = "/book/add", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<BookDto> insertBook(@RequestBody BookRequest bookRequest) {
        return bookService.insert(bookRequest.title(), bookRequest.authorId(), bookRequest.genresIds());
    }
}
