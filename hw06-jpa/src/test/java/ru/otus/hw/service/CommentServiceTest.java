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
import ru.otus.hw.mapper.LibraryMapperImpl;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaCommentRepository;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import({JpaCommentRepository.class,
        JpaBookRepository.class,
        CommentServiceImpl.class,
        LibraryMapperImpl.class
})
@DisplayName("Интеграционный тест на проверку сервиса комментариев")
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @ParameterizedTest
    @MethodSource("getBooksIds")
    @DisplayName("должен возвращать список комментариев по книге")
    void shouldReturnCommentsForBookId(Long bookId) {
        var comments = commentService.findByBookId(bookId);
        assertThat(comments).isNotEmpty();
        comments.forEach( commentDto -> {
            var book = commentDto.book();
            assertThat(book).isNotNull();
            assertThat(book.title()).isNotNull();
            assertThat(book.author()).isNotNull();
            assertThat(book.author().fullName()).isNotNull();
            assertThat(book.genres().size()).isGreaterThan(0);
        });
    }

    @Test
    @DisplayName("должен сохранять новый коммент")
    void shouldSaveNewComment() {
        var comment = commentService.insert("NewComment", 2);
        assertThat(comment.id()).isGreaterThan(0);
        var savedComment = commentService.findById(comment.id());
        assertThat(savedComment)
                .isPresent()
                .get()
                .isEqualTo(comment);
    }

    @Test
    @DisplayName("должен обновлять сохраненный коммент")
    void shouldUpdateSavedComment() {
        var comment = commentService.insert("NewComment", 2);
        assertThat(comment.id()).isGreaterThan(0);
        var updatedComment = commentService.update(comment.id(), "Updated Comment", 3);
        var actualComment = commentService.findById(comment.id());
        assertThat(actualComment)
                .isPresent()
                .get()
                .isEqualTo(updatedComment);
    }

    @Test
    @DisplayName("должен удалить коммент по id")
    void shouldDeleteCommentById() {
        var comment = commentService.insert("NewComment", 2);
        assertThat(comment.id()).isGreaterThan(0);
        commentService.deleteById(comment.id());
        var deletedComment = commentService.findById(comment.id());
        assertThat(deletedComment).isEmpty();
    }

    private static List<Long> getBooksIds() {
        return LongStream.range(1, 4).boxed().toList();
    }
}
