package ru.otus.hw.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import(JpaAuthorRepository.class)
@DisplayName("Репозиторий на основе JPA для работы с авторами")
class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository repository;

    private List<Author> dbAuthors;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
    }

    @ParameterizedTest
    @MethodSource("getDbAuthors")
    @DisplayName("должен загружать автора по id")
    void shouldReturnCorrectAuthorById(Author expectedAuthor) {
        var actualAuthor = repository.findById(expectedAuthor.getId());
        assertThat(actualAuthor)
                .isPresent()
                .get()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("должен загружать список всех авторов")
    void shouldReturnCorrectAuthorsList() {
        var actualAuthors = repository.findAll();
        var expectedAuthors = dbAuthors;
        assertThat(actualAuthors).containsExactlyElementsOf(expectedAuthors);
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }
}