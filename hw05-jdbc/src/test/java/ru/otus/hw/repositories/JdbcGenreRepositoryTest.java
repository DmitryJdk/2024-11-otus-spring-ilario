package ru.otus.hw.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@JdbcTest
@Import(JdbcGenreRepository.class)
@DisplayName("Репозиторий на основе Jdbc для работы с жанрами")
class JdbcGenreRepositoryTest {

    @Autowired
    private JdbcGenreRepository repositoryJdbc;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp() {
        dbGenres = getDbGenres();
    }

    @Test
    @DisplayName("должен загружать список всех жанров")
    void shouldReturnCorrectGenresList() {
        var actualGenres = repositoryJdbc.findAll();
        var expectedGenres = dbGenres;

        assertThat(actualGenres).containsExactlyElementsOf(expectedGenres);
    }

    @Test
    @DisplayName("должен загружать список жанров по id")
    void shouldReturnCorrectGenreByIds() {
        var expectedGenres = dbGenres;
        var actualGenres = repositoryJdbc.findAllByIds(
                dbGenres.stream()
                        .map(Genre::getId)
                        .collect(Collectors.toSet())
        );

        assertThat(actualGenres).containsExactlyElementsOf(expectedGenres);
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 7).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }
}