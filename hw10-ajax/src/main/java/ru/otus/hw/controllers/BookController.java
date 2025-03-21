package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookRequest;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    public List<BookDto> findAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/book/{id}")
    public BookDto getBook(@PathVariable String id) {
        return bookService.findById(id).orElseThrow(() -> new IllegalArgumentException("Книга не найдена"));
    }

    @PostMapping("/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable String id, @RequestBody BookRequest bookRequest) {
        bookService.update(id, bookRequest.title(), bookRequest.authorId(), bookRequest.genresIds());
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PostMapping("/book/add")
    public BookDto insertBook(@RequestBody BookRequest bookRequest) {
        return bookService.insert(bookRequest.title(), bookRequest.authorId(), bookRequest.genresIds());
    }
}
