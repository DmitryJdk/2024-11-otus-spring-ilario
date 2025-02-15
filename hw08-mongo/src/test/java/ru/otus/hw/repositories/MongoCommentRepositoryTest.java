package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.util.TestDataUtil;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Mongo для работы с комментариями")
@DataMongoTest
public class MongoCommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Test
    @DisplayName("должен возвращать комментарии по id книги")
    void shouldReturnCorrectCommentsByBookId() {
        var book = TestDataUtil.books.get(0);
        var expectedComments = TestDataUtil.comments.stream()
                .filter(c -> c.getBook().equals(book))
                .toList();
        assertThat(book.getId()).isNotNull();
        var actualComments = repository.findByBookId(book.getId());
        assertThat(actualComments).hasSizeGreaterThan(0);
        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
    }
}
