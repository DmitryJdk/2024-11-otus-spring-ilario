package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.mapper.LibraryMapperImpl;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import({CommentServiceImpl.class,
        BookServiceImpl.class,
        LibraryMapperImpl.class
})
@DisplayName("Интеграционный тест на проверку каскадного удаления")
public class BookCommentCascadeTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("должен удалять комментарии при удалении книги")
    void shouldDeleteCommentsWhenBookDeleted() {
        var bookDto = bookService.insert("test book", "test author", Set.of("Genre1", "Genre2"));
        assertThat(bookDto.id()).isNotNull();

        var commentDto1 = commentService.insert("test1", bookDto.id());
        assertThat(commentDto1.id()).isNotNull();
        var commentDto2 = commentService.insert("test2", bookDto.id());
        assertThat(commentDto2.id()).isNotNull();

        var actualComments = commentService.findByBookId(bookDto.id());
        assertThat(actualComments).containsExactlyInAnyOrderElementsOf(List.of(commentDto1, commentDto2));

        bookService.deleteById(bookDto.id());
        var deletedBook = bookService.findById(bookDto.id());
        assertThat(deletedBook).isEmpty();

        var actualCommentsAfterBookDeleted = commentService.findByBookId(bookDto.id());
        assertThat(actualCommentsAfterBookDeleted).isEmpty();

        var actualCommentDto1 = commentService.findById(commentDto1.id());
        assertThat(actualCommentDto1).isNotPresent();

        var actualCommentDto2 = commentService.findById(commentDto2.id());
        assertThat(actualCommentDto2).isNotPresent();
    }
}
