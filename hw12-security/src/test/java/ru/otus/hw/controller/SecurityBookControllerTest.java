package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.dto.BookRequest;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;
import ru.otus.hw.util.TestDataUtil;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@DisplayName("Тесты контроллера книг")
public class SecurityBookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BookService bookService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("не должен отдавать список книг")
    void shouldNotReturnAllBooks() throws Exception {
        mvc.perform(get("/api/book"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("не должен отдавать книгу по id")
    void shouldNotOpenBookGetBookWithBook() throws Exception {
        var expectedBookDto = TestDataUtil.bookDto.get(0);
        mvc.perform(get("/api/book/" + expectedBookDto.id()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("не должен редактировать книгу")
    void shouldNotUpdateBookAndReturnToBookPage() throws Exception {
        var book = TestDataUtil.books.get(0);
        var genresIds = book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        var requestBody = new BookRequest(book.getTitle(), book.getAuthor().getId(), genresIds);
        mvc.perform(put("/api/book/" + book.getId())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("не должен удалять книгу")
    void shouldNotDeleteBook() throws Exception {
        var expectedBookDto = TestDataUtil.bookDto.get(0);
        mvc.perform(delete("/api/book/" + expectedBookDto.id()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("не должен добавлять книгу")
    void shouldNotInsertBook() throws Exception {
        var book = TestDataUtil.books.get(0);
        var genresIds = book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        var requestBody = new BookRequest(book.getTitle(), book.getAuthor().getId(), genresIds);
        mvc.perform(post("/api/book")
                .contentType("application/json")
                .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().is4xxClientError());
    }
}
