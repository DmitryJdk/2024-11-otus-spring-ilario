package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.hw.util.TestDataUtil;

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
        StepVerifier.create(actualAuthors)
                .expectNextSequence(expectedAuthors)
                .expectComplete()
                .verify();
    }

}
