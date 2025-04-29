package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final LibraryMapper libraryMapper;

    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id).map(libraryMapper::bookToBookDto);
    }

    @Override
    @Transactional(readOnly = true)
    @PostFilter("hasPermission(filterObject.author, 'READ')")
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(libraryMapper::bookToBookDto)
                .toList();
    }

    @Override
    @Transactional
    public BookDto insert(String title, long authorId, Set<Long> genresIds) {
        return libraryMapper.bookToBookDto(save(0, title, authorId, genresIds));
    }

    @Override
    @Transactional
    public BookDto update(long id, String title, long authorId, Set<Long> genresIds) {
        return libraryMapper.bookToBookDto(save(id, title, authorId, genresIds));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }

    private Book save(long id, String title, long authorId, Set<Long> genresIds) {
        if (isEmpty(genresIds)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genres = genreRepository.findAllById(genresIds);
        if (isEmpty(genres) || genresIds.size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with ids %s not found".formatted(genresIds));
        }

        var book = new Book(id, title, author, genres);
        return bookRepository.save(book);
    }
}
