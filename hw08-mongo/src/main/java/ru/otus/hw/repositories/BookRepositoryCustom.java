package ru.otus.hw.repositories;

import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;

import java.util.List;

public interface BookRepositoryCustom {

    List<Author> findAllAuthors();

    List<Genre> findAllGenres();
}
