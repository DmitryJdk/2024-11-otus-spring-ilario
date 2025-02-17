package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final LibraryMapper libraryMapper;

    private final BookRepository bookRepository;

    @Override
    public List<GenreDto> findAll() {
        return bookRepository.findAllGenres()
                .stream()
                .map(libraryMapper::genreToGenreDto)
                .toList();
    }
}
