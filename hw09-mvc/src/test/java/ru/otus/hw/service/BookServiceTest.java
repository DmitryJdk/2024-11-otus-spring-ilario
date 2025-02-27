package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.mapper.LibraryMapperImpl;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.util.TestDataUtil;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@Import({BookServiceImpl.class,
        LibraryMapperImpl.class
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
            assertThat(book.author()).isNotNull();
            assertThat(book.genres().size()).isGreaterThan(0);
        });
    }

    @Test
    @DisplayName("должен сохранять новую книгу")
    void shouldSaveNewBook() {
        var authorId = TestDataUtil.getAuthorId(1);
        var genreId = TestDataUtil.getGenresId(1, 4);
        var book = bookService.insert("NewBook_4", authorId, genreId);
        assertThat(book.id()).isNotNull();
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
                () -> bookService.insert("test", "Author", Set.of())
        );
    }

    @Test
    @DisplayName("должен выбрасывать исключение, когда автора нет")
    void shouldThrowExceptionWhenAuthorNotSet() {
        assertThrows(
                IllegalArgumentException.class,
                () -> bookService.insert("test", "", Set.of("test"))
        );
    }

    @Test
    @DisplayName("должен обновлять сохраненную книгу")
    void shouldUpdateSavedComment() {
        var authorId = TestDataUtil.getAuthorId(1);
        var genreId = TestDataUtil.getGenresId(1, 2);
        var book = bookService.insert("NewBook_4", authorId, genreId);
        assertThat(book.id()).isNotNull();
        var updatedAuthorId = TestDataUtil.getAuthorId(0);
        var updatedGenresIds = TestDataUtil.getGenresId(0, 5);
        var updatedBook = bookService.update(book.id(), "New Title 2", updatedAuthorId, updatedGenresIds);
        var actualBook = bookService.findById(book.id());
        assertThat(actualBook)
                .isPresent()
                .get()
                .isEqualTo(updatedBook);
    }

    @Test
    @DisplayName("должен удалить книгу по id")
    void shouldDeleteBookById() {
        var authorId = TestDataUtil.getAuthorId(2);
        var genreId = TestDataUtil.getGenresId(3, 4);
        var book = bookService.insert("NewBook_4", authorId, genreId);
        assertThat(book.id()).isNotNull();
        bookService.deleteById(book.id());
        var deletedBook = bookService.findById(book.id());
        assertThat(deletedBook).isEmpty();
    }

}
