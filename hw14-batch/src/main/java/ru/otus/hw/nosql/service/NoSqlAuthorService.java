package ru.otus.hw.nosql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.nosql.model.Author;
import ru.otus.hw.nosql.repository.NoSqlAuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoSqlAuthorService {

    private final NoSqlAuthorRepository noSqlAuthorRepository;

    public List<Author> getAuthors() {
        return noSqlAuthorRepository.findAll();
    }

    public void clear() {
        noSqlAuthorRepository.deleteAll();
    }

}
