package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.exceptions.EntityNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final LibraryMapper libraryMapper;

    @Override
    public Optional<BookDto> findById(String id) {
        return bookRepository.findById(id).map(libraryMapper::bookToBookDto);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(libraryMapper::bookToBookDto)
                .toList();
    }

    @Override
    @Transactional
    public BookDto insert(String title, String authorId, Set<String> genresId) {
        return libraryMapper.bookToBookDto(save(null, title, authorId, genresId));
    }

    @Override
    @Transactional
    public BookDto update(String id, String title, String authorId, Set<String> genresId) {
        return libraryMapper.bookToBookDto(save(id, title, authorId, genresId));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }

    private Book save(String id, String title, String authorId, Set<String> genresId) {
        if (isEmpty(genresId)) {
            throw new IllegalArgumentException("Genres must not be null");
        }
        if (StringUtils.isEmpty(authorId)) {
            throw new IllegalArgumentException("Author must not be null");
        }
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("No author found"));
        Set<Genre> genres = new HashSet<>(genreRepository.findAllById(genresId));
        if (genres.size() != genresId.size()) {
            throw new EntityNotFoundException("Genres not found");
        }
        var book = new Book(id, title, author, genres);
        return bookRepository.save(book);
    }
}
