package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final LibraryMapper libraryMapper;

    private final BookRepository bookRepository;

    @Override
    public List<AuthorDto> findAll() {
        return bookRepository.findAllAuthors()
                .stream()
                .map(libraryMapper::authorToAuthorDto)
                .toList();
    }
}
