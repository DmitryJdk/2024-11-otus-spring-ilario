package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.mapper.LibraryMapperImpl;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.CommentServiceImpl;
import ru.otus.hw.util.TestDataUtil;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import({CommentServiceImpl.class,
        LibraryMapperImpl.class
})
@DisplayName("Интеграционный тест на проверку сервиса комментариев")
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private LibraryMapper libraryMapper;

    @Test
    @DisplayName("должен возвращать список комментариев по книге")
    void shouldReturnCommentsForBookId() {
        var book = TestDataUtil.books.get(0);
        var expectedComments = TestDataUtil.comments.stream()
                .filter(c -> c.getBook().equals(book))
                .map(libraryMapper::commentToCommentDto)
                .toList();
        var comments = commentService.findByBookId(book.getId());
        assertThat(comments).containsExactlyElementsOf(expectedComments);
    }

    @Test
    @DisplayName("должен сохранять новый коммент")
    void shouldSaveNewComment() {
        var book = TestDataUtil.books.get(1);
        var comment = commentService.insert("NewComment", book.getId());
        assertThat(comment.id()).isNotNull();
        var savedComment = commentService.findById(comment.id());
        assertThat(savedComment)
                .isPresent()
                .get()
                .isEqualTo(comment);
    }

    @Test
    @DisplayName("должен обновлять сохраненный коммент")
    void shouldUpdateSavedComment() {
        var book = TestDataUtil.books.get(1);
        var comment = commentService.insert("NewComment", book.getId());
        assertThat(comment.id()).isNotNull();
        var newBook = TestDataUtil.books.get(2);
        var updatedComment = commentService.update(comment.id(), "Updated Comment", newBook.getId());
        var actualComment = commentService.findById(comment.id());
        assertThat(actualComment)
                .isPresent()
                .get()
                .isEqualTo(updatedComment);
    }

    @Test
    @DisplayName("должен удалить коммент по id")
    void shouldDeleteCommentById() {
        var book = TestDataUtil.books.get(0);
        var comment = commentService.insert("NewComment", book.getId());
        assertThat(comment.id()).isNotNull();
        commentService.deleteById(comment.id());
        var deletedComment = commentService.findById(comment.id());
        assertThat(deletedComment).isEmpty();
    }
}
