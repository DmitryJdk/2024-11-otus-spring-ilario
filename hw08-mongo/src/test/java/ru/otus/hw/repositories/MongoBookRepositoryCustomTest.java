package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.util.TestDataUtil;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Mongo для работы с вложенными сущностями ")
@DataMongoTest
public class MongoBookRepositoryCustomTest {

    @Autowired
    private BookRepository repository;

    @Test
    @DisplayName("Должен грузить список всех авторов без повторений")
    void shouldReturnAllAuthorsWithoutRepeating() {
        var actualAuthors = repository.findAllAuthors();
        var expectedAuthors = TestDataUtil.authors;
        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @Test
    @DisplayName("Должен грузить список всех жанров без повторений")
    void shouldReturnAllGenresWithoutRepeating() {
        var actualGenres = repository.findAllGenres();
        var expectedGenres = TestDataUtil.genres;
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }
}
