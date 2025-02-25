package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.util.TestDataUtil;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
    }
}