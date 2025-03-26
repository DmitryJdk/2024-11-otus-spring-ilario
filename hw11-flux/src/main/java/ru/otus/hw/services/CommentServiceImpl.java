package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final LibraryMapper libraryMapper;

    @Override
    public Mono<CommentDto> findById(String id) {
        return commentRepository
                .findById(id)
                .map(libraryMapper::commentToCommentDto);
    }

    @Override
    public Mono<CommentDto> insert(String text, String bookId) {
        return save(null, text, bookId)
                .map(libraryMapper::commentToCommentDto);
    }

    @Override
    public Mono<CommentDto> update(String id, String text, String bookId) {
        return save(id, text, bookId)
                .map(libraryMapper::commentToCommentDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return commentRepository.deleteById(id);
    }

    @Override
    public Flux<CommentDto> findByBookId(String id) {
        return commentRepository.findByBookId(id)
                .map(libraryMapper::commentToCommentDto);
    }

    private Mono<Comment> save(String id, String text, String bookId) {
        Mono<Book> book = bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book not found for comments")));
        var comment = new Comment();
        comment.setId(id);
        comment.setText(text);
        return book.flatMap(b -> {
                    comment.setBook(b);
                    return commentRepository.save(comment);
                }
        );
    }
}
