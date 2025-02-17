package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

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
    public BookDto insert(String title, String authorFullName, Set<String> genreNames) {
        return libraryMapper.bookToBookDto(save(null, title, authorFullName, genreNames));
    }

    @Override
    @Transactional
    public BookDto update(String id, String title, String authorFullName, Set<String> genreNames) {
        return libraryMapper.bookToBookDto(save(id, title, authorFullName, genreNames));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }

    private Book save(String id, String title, String fullName, Set<String> genreNames) {
        if (isEmpty(genreNames)) {
            throw new IllegalArgumentException("Genres must not be null");
        }
        if (StringUtils.isEmpty(fullName)) {
            throw new IllegalArgumentException("Author must not be null");
        }
        Set<Genre> genres = genreNames.stream().map(Genre::new).collect(Collectors.toSet());
        var book = new Book(id, title, new Author(fullName), genres);
        return bookRepository.save(book);
    }
}
