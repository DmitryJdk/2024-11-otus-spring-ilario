package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.hw.util.TestDataUtil;

@DisplayName("Репозиторий Mongo для работы с книгами ")
@DataMongoTest
class MongoBookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = repository.findAll();
        var expectedBooks = TestDataUtil.books;
        StepVerifier.create(actualBooks)
                .expectNextSequence(expectedBooks)
                .expectComplete()
                .verify();
    }
}