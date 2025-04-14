package ru.otus.hw.nosql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.nosql.model.Genre;
import ru.otus.hw.nosql.repository.NoSqlGenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoSqlGenreService {

    private final NoSqlGenreRepository noSqlGenreRepository;

    public List<Genre> getGenres() {
        return noSqlGenreRepository.findAll();
    }

    public void clear() {
        noSqlGenreRepository.deleteAll();
    }

}
