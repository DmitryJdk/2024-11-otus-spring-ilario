package ru.otus.hw.nosql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.nosql.model.NoSqlBook;
import ru.otus.hw.nosql.repository.NoSqlBookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoSqlBookService {

    private final NoSqlBookRepository noSqlBookRepository;

    public List<NoSqlBook> getBooks() {
        return noSqlBookRepository.findAll();
    }

    public void clear() {
        noSqlBookRepository.deleteAll();
    }

}
