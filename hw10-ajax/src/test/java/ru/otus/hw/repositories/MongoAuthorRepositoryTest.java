package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.util.TestDataUtil;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Mongo для работы с авторами")
@DataMongoTest
public class MongoAuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("должен грузить список всех авторов без повторений")
    void shouldReturnAllAuthorsWithoutRepeating() {
        var actualAuthors = repository.findAll();
        var expectedAuthors = TestDataUtil.authors;
        assertThat(actualAuthors).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

}
