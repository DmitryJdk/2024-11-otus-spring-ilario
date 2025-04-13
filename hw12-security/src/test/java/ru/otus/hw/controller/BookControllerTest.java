package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.dto.BookRequest;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;
import ru.otus.hw.util.TestDataUtil;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@DisplayName("Тесты контроллера книг")
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BookService bookService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("должен отдавать список книг")
    void shouldReturnAllBooks() throws Exception {
        var expectedBooksDto = TestDataUtil.bookDto;
        when(bookService.findAll()).thenReturn(expectedBooksDto);
        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBooksDto)));
    }

    @Test
    @DisplayName("должен отдавать книгу по id")
    void shouldOpenBookGetBookWithBook() throws Exception {
        var expectedBookDto = TestDataUtil.bookDto.get(0);
        when(bookService.findById(expectedBookDto.id())).thenReturn(Optional.of(expectedBookDto));
        mvc.perform(get("/api/book/" + expectedBookDto.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBookDto)));
    }

    @Test
    @DisplayName("должен успешно редактировть книгу")
    void shouldUpdateBookAndReturnToBookPage() throws Exception {
        var book = TestDataUtil.books.get(0);
        var genresIds = book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        var requestBody = new BookRequest(book.getTitle(), book.getAuthor().getId(), genresIds);
        mvc.perform(put("/api/book/" + book.getId())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("должен успешно удалять книгу")
    void shouldReturnToBookPageAfterDelete() throws Exception {
        var expectedBookDto = TestDataUtil.bookDto.get(0);
        mvc.perform(delete("/api/book/" + expectedBookDto.id()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("должен успешно добавлять книгу")
    void shouldReturnToBookPageAfterInsert() throws Exception {
        var book = TestDataUtil.books.get(0);
        var genresIds = book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        var requestBody = new BookRequest(book.getTitle(), book.getAuthor().getId(), genresIds);
        mvc.perform(post("/api/book")
                .contentType("application/json")
                .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }
}
