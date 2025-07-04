package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final LibraryMapper libraryMapper;

    @Override
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream()
                .map(libraryMapper::authorToAuthorDto)
                .toList();
    }
}
