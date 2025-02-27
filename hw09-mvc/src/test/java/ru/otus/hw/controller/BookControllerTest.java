package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.util.TestDataUtil;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
@Import(BookController.class)
@DisplayName("Тесты контроллера книг")
@MockitoBean(types = {AuthorService.class, GenreService.class})
public class BookControllerTest {

    @Configuration
    static class Config {}

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BookService bookService;

    @Test
    @DisplayName("должен открывать страницу со всеми книгами")
    void shouldOpenPageWithBooks() throws Exception {
        var expectedBooksDto = TestDataUtil.bookDto;
        when(bookService.findAll()).thenReturn(expectedBooksDto);
        mvc.perform(get("/book"))
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", expectedBooksDto));
    }

    @Test
    @DisplayName("должен открывать страницу редактирования")
    void shouldOpenBookEditPageWithBook() throws Exception {
        var expectedBookDto = TestDataUtil.bookDto.get(0);
        when(bookService.findById(expectedBookDto.id())).thenReturn(Optional.of(expectedBookDto));
        mvc.perform(get("/book/" + expectedBookDto.id()))
                .andExpect(view().name("book_edit"))
                .andExpect(model().attribute("book", expectedBookDto));
    }

    @Test
    @DisplayName("должен возвращаться на страницу со списком книг после обновления книги")
    void shouldUpdateBookAndReturnToBookPage() throws Exception {
        var book = TestDataUtil.books.get(0);
        var genres = book.getGenres().stream().map(Genre::getId).collect(Collectors.joining(","));
        mvc.perform(post("/book/" + book.getId())
                        .queryParam("title", "ttt")
                        .queryParam("authorId", book.getAuthor().getId())
                        .queryParam("genresIds", genres)
                )
                .andExpect(view().name("redirect:/book"));
    }

    @Test
    @DisplayName("должен возвращаться на страницу со списком книг после удаления книги")
    void shouldReturnToBookPageAfterDelete() throws Exception {
        var expectedBookDto = TestDataUtil.bookDto.get(0);
        mvc.perform(delete("/book/" + expectedBookDto.id()))
                .andExpect(view().name("redirect:/book"));
    }

    @Test
    @DisplayName("должен открывать страницу добавления книги")
    void shouldOpenAddingBookPage() throws Exception {
        mvc.perform(get("/book/add"))
                .andExpect(view().name("book_add"));
    }

    @Test
    @DisplayName("должен возвращаться на страницу со списком книг после добавления книги")
    void shouldReturnToBookPageAfterInsert() throws Exception {
        var book = TestDataUtil.books.get(0);
        var genres = book.getGenres().stream().map(Genre::getId).collect(Collectors.joining(","));
        mvc.perform(post("/book/add")
                        .queryParam("title", "ttt")
                        .queryParam("authorId", book.getAuthor().getId())
                        .queryParam("genresIds", genres)
                )
                .andExpect(view().name("redirect:/book"));
    }
}
