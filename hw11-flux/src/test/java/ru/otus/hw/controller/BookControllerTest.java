package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookRequest;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;
import ru.otus.hw.util.TestDataUtil;

import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(BookController.class)
@Import(BookController.class)
@DisplayName("Тесты контроллера книг")
public class BookControllerTest {

    @Configuration
    static class Config {}

    @MockitoBean
    private BookService bookService;

    @Autowired
    private WebTestClient mvc;

    @Test
    @DisplayName("должен отдавать список книг")
    void shouldReturnAllBooks() {
        var expectedBooksDto = TestDataUtil.bookDto;
        when(bookService.findAll()).thenReturn(Flux.fromIterable(expectedBooksDto));

        var result = mvc.get()
                .uri("/book")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_NDJSON_VALUE)
                .returnResult(BookDto.class);

        StepVerifier.create(result.getResponseBody())
                .expectNextSequence(expectedBooksDto)
                .verifyComplete();
    }

    @Test
    @DisplayName("должен отдавать книгу по id")
    void shouldOpenBookGetBookWithBook() {
        var expectedBookDto = TestDataUtil.bookDto.get(0);
        when(bookService.findById(expectedBookDto.id()))
                .thenReturn(Mono.just(expectedBookDto));
        var result = mvc.get()
                .uri("/book/" + expectedBookDto.id())
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_NDJSON_VALUE)
                .returnResult(BookDto.class);
        StepVerifier.create(result.getResponseBody())
                .expectNext(expectedBookDto)
                .verifyComplete();
    }

    @Test
    @DisplayName("должен успешно редактировть книгу")
    void shouldUpdateBookAndReturnToBookPage() {
        var book = TestDataUtil.books.get(0);
        var genresIds = book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        var requestBody = new BookRequest(book.getTitle(), book.getAuthor().getId(), genresIds);
        when(bookService.update(any(), any(), any(),any())).thenReturn(Mono.empty());
        mvc.post()
                .uri("/book/" + book.getId())
                .body(Mono.just(requestBody), BookRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("должен успешно удалять книгу")
    void shouldReturnToBookPageAfterDelete() {
        var expectedBookDto = TestDataUtil.bookDto.get(0);
        when(bookService.deleteById(expectedBookDto.id())).thenReturn(Mono.empty());
        mvc.delete()
                .uri("/book/" + expectedBookDto.id())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("должен успешно добавлять книгу")
    void shouldReturnToBookPageAfterInsert() {
        var book = TestDataUtil.books.get(0);
        var genresIds = book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        var requestBody = new BookRequest(book.getTitle(), book.getAuthor().getId(), genresIds);
        mvc.post()
                .uri("/book/add")
                .body(Mono.just(requestBody), BookRequest.class)
                .exchange()
                .expectStatus().isOk();
    }
}
