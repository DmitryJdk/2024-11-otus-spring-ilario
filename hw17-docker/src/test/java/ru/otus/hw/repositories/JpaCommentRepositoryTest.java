package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.util.Pair;
import ru.otus.hw.models.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями")
@DataJpaTest
public class JpaCommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @ParameterizedTest
    @MethodSource("getBookComments")
    @DisplayName("должен возвращать комментарии по id книги")
    void shouldReturnCorrectCommentsByBookId(Pair<Long, List<Long>> bookComments) {
        var expectedComments = new ArrayList<Comment>();
        bookComments.getSecond().forEach(it -> expectedComments.add(
                repository.findById(it)
                        .orElseThrow(() -> new IllegalArgumentException("Test data is not correct")))
        );
        var actualComments = repository.findByBookId(bookComments.getFirst());
        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
    }

    private static Stream<Pair<Long, List<Long>>> getBookComments() {
        return Stream.of(
                Pair.of(1L, List.of(1L, 2L)),
                Pair.of(2L, List.of(3L, 4L)),
                Pair.of(3L, List.of(5L, 6L))
        );
    }
}
