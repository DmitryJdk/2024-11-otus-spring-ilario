package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.util.Pair;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями")
@DataJpaTest
@Import({JpaCommentRepository.class, JpaBookRepository.class})
public class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("должен возвращать комментарий по id")
    void shouldReturnCorrectCommentById() {
        var expectedComment = entityManager.find(Comment.class, 1L);
        var actualComment = repository.findById(1L);
        assertThat(actualComment)
                .isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @ParameterizedTest
    @MethodSource("getBookComments")
    @DisplayName("должен возвращать комментарии по id книги")
    void shouldReturnCorrectCommentsByBookId(Pair<Long, List<Long>> bookComments) {
        var expectedComments = new ArrayList<Comment>();
        bookComments.getSecond().forEach(it ->
                expectedComments.add(entityManager.find(Comment.class, it))
        );
        var actualComments = repository.findByBookId(bookComments.getFirst());
        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
    }

    @Test
    @DisplayName("должен удалить комментарий по id")
    void shouldDeleteCommentById() {
        var actualComment = entityManager.find(Comment.class, 3L);
        assertThat(actualComment).isNotNull();
        entityManager.detach(actualComment);
        repository.deleteById(3L);
        assertThat(entityManager.find(Comment.class, 3L)).isNull();
    }

    @Test
    @DisplayName("должен сохранять новый комментарий")
    void shouldSaveNewComment() {
        var existingBook = getExistingBook();
        var comment = new Comment(0, "New Comment", existingBook);
        repository.save(comment);
        assertThat(comment.getId()).isNotEqualTo(0);
        var actualComment = entityManager.find(Comment.class, comment.getId());
        assertThat(actualComment)
                .isNotNull()
                .isEqualTo(comment);
    }

    @Test
    @DisplayName("должен обновлять существующий комментарий")
    void shouldUpdateComment() {
        var existingBook = getExistingBook();
        var comment = new Comment(0, "New Comment", existingBook);
        entityManager.persist(comment);
        entityManager.detach(comment);
        comment.setText("Updated Text");
        repository.save(comment);
        var actualComment = entityManager.find(Comment.class, comment.getId());
        assertThat(actualComment).isEqualTo(comment);
    }

    private Book getExistingBook() {
        var author = new Author(1L, "Author_1");
        var genres = List.of(
                new Genre(1L, "Genre_1"),
                new Genre(2L, "Genre_2")
        );
        return new Book(1L, "BookTitle_1", author, genres);
    }

    private static Stream<Pair<Long, List<Long>>> getBookComments() {
        return Stream.of(
                Pair.of(1L, List.of(1L, 2L)),
                Pair.of(2L, List.of(3L, 4L)),
                Pair.of(3L, List.of(5L, 6L))
        );
    }
}
