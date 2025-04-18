package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final LibraryMapper libraryMapper;

    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(libraryMapper::genreToGenreDto)
                .toList();
    }
}
