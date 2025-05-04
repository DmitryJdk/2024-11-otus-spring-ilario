package ru.otus.hw.sql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.sql.dto.BookDto;
import ru.otus.hw.sql.mapper.LibraryMapper;
import ru.otus.hw.sql.repository.SqlBookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SqlBookService {

    private final SqlBookRepository sqlBookRepository;

    private final LibraryMapper libraryMapper;

    @Transactional(readOnly = true)
    public List<BookDto> getBooks() {
        return sqlBookRepository.findAll().stream()
                .map(libraryMapper::bookToBookDto)
                .toList();
    }
}
