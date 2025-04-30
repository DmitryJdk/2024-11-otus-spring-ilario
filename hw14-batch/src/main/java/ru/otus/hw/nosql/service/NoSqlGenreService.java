package ru.otus.hw.nosql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.nosql.model.NoSqlGenre;
import ru.otus.hw.nosql.repository.NoSqlGenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoSqlGenreService {

    private final NoSqlGenreRepository noSqlGenreRepository;

    public List<NoSqlGenre> getGenres() {
        return noSqlGenreRepository.findAll();
    }

    public void clear() {
        noSqlGenreRepository.deleteAll();
    }

}
