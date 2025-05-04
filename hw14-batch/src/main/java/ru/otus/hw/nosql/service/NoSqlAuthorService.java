package ru.otus.hw.nosql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.nosql.model.NoSqlAuthor;
import ru.otus.hw.nosql.repository.NoSqlAuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoSqlAuthorService {

    private final NoSqlAuthorRepository noSqlAuthorRepository;

    public List<NoSqlAuthor> getAuthors() {
        return noSqlAuthorRepository.findAll();
    }

    public void clear() {
        noSqlAuthorRepository.deleteAll();
    }

}
