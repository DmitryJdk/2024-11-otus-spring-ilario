package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.LibraryMapperImpl;
import ru.otus.hw.repositories.*;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.CommentServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import({BookServiceImpl.class,
        LibraryMapperImpl.class,
        JpaBookRepository.class,
        JpaAuthorRepository.class,
        JpaGenreRepository.class
})
@DisplayName("Интеграционный тест на проверку сервиса книг")
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("должен возвращать список всех книг")
    void shouldReturnAllBooks() {
        var books = bookService.findAll();
        assertThat(books).isNotEmpty();

        books.forEach(book -> {
            assertThat(book.title()).isNotNull();
            assertThat(book.author()).isNotNull();
            assertThat(book.author().fullName()).isNotNull();
            assertThat(book.genres().size()).isGreaterThan(0);
        });
    }

    @Test
    @DisplayName("должен сохранять новую книгу")
    void shouldSaveNewBook() {
        var book = bookService.insert("NewBook_4", 1, Set.of(1L, 4L));
        assertThat(book.id()).isGreaterThan(0);
        var bookFromRepository = bookService.findById(book.id());
        assertThat(bookFromRepository).
                isPresent()
                .get()
                .isEqualTo(book);
    }

    @Test
    @DisplayName("должен выбрасывать исключение, когда нет жанров")
    void shouldThrowExceptionWhenGenresEmpty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> bookService.insert("test", 1, Set.of())
        );
    }

    @Test
    @DisplayName("должен выбрасывать исключение, когда один жанр из списка не найден")
    void shouldThrowExceptionWhenOneOfGenresNotFound() {
        assertThrows(
                EntityNotFoundException.class,
                () -> bookService.insert("test", 1, Set.of(1L, 100L))
        );
    }

    @Test
    @DisplayName("должен выбрасывать исключение, когда все жанры не найдены")
    void shouldThrowExceptionWhenAllGenresNotFound() {
        assertThrows(
                EntityNotFoundException.class,
                () -> bookService.insert("test", 1, Set.of(100L, 101L))
        );
    }

    @Test
    @DisplayName("должен обновлять сохраненную книгу")
    void shouldUpdateSavedComment() {
        var book = bookService.insert("NewBook", 1, Set.of(1L));
        assertThat(book.id()).isGreaterThan(0);
        var updatedBook = bookService.update(book.id(), "New Title 2", 2, Set.of(2L, 3L));
        var actualBook = bookService.findById(book.id());
        assertThat(actualBook)
                .isPresent()
                .get()
                .isEqualTo(updatedBook);
    }

    @Test
    @DisplayName("должен удалить книгу по id")
    void shouldDeleteBookById() {
        var book = bookService.insert("NewBook", 1, Set.of(1L));
        assertThat(book.id()).isGreaterThan(0);
        bookService.deleteById(book.id());
        var deletedBook = bookService.findById(book.id());
        assertThat(deletedBook).isEmpty();
    }

}
