package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.hw.util.TestDataUtil;

@DisplayName("Репозиторий Mongo для работы с жанрами")
@DataMongoTest
public class MongoGenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Test
    @DisplayName("Должен грузить список всех жанров без повторений")
    void shouldReturnAllGenresWithoutRepeating() {
        var actualGenres = repository.findAll();
        var expectedGenres = TestDataUtil.genres;
        StepVerifier.create(actualGenres)
                .expectNextSequence(expectedGenres)
                .expectComplete()
                .verify();
    }
}
