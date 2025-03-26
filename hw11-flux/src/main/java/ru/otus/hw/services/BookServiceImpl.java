package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    private final LibraryMapper libraryMapper;

    @Override
    public Mono<BookDto> findById(String id) {
        return bookRepository
                .findById(id)
                .map(libraryMapper::bookToBookDto);
    }

    @Override
    public Flux<BookDto> findAll() {
        return bookRepository.findAll()
                .map(libraryMapper::bookToBookDto);
    }

    @Override
    public Mono<BookDto> insert(String title, String authorId, Set<String> genresIds) {
        return save(null, title, authorId, genresIds)
                .map(libraryMapper::bookToBookDto);
    }

    @Override
    public Mono<BookDto> update(String id, String title, String authorId, Set<String> genreNames) {
        return save(id, title, authorId, genreNames)
                .map(libraryMapper::bookToBookDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return commentRepository.deleteByBookId(id)
                .then(bookRepository.deleteById(id));
    }

    private Mono<Book> save(String id, String title, String authorId, Set<String> genresId) {
        if (StringUtils.isEmpty(authorId)) {
            throw new IllegalArgumentException("Author must not be null");
        }
        if (isEmpty(genresId)) {
            throw new IllegalArgumentException("Genres must not be null");
        }
        Mono<Author> author = authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("No author found")));
        Flux<Genre> genres = genreRepository.findAllById(genresId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Genres not found")));
        var book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setGenres(new HashSet<>());

        return genres.map(g -> book.getGenres().add(g))
                .then(author)
                .flatMap(a -> {
                    book.setAuthor(a);
                    return bookRepository.save(book);
                });
    }
}
