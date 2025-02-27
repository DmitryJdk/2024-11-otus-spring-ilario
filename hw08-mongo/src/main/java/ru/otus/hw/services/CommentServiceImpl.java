package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final LibraryMapper libraryMapper;

    @Override
    public Optional<CommentDto> findById(String id) {
        return commentRepository.findById(id).map(libraryMapper::commentToCommentDto);
    }

    @Override
    public CommentDto insert(String text, String bookId) {
        return libraryMapper.commentToCommentDto(save(null, text, bookId));
    }

    @Override
    public CommentDto update(String id, String text, String bookId) {
        return libraryMapper.commentToCommentDto(save(id, text, bookId));
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> findByBookId(String id) {
        return commentRepository.findByBookId(id)
                .stream()
                .map(libraryMapper::commentToCommentDto)
                .toList();
    }

    private Comment save(String id, String text, String bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found for comments"));
        var comment = new Comment(id, text, book);
        return commentRepository.save(comment);
    }
}
