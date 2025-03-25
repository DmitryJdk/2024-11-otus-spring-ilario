package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;
import ru.otus.hw.mapper.LibraryMapperImpl;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.CommentServiceImpl;
import ru.otus.hw.util.TestDataUtil;

import java.util.List;

@DataMongoTest
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
        var authorId = TestDataUtil.getAuthorId(0);
        var genreId = TestDataUtil.getGenresId(0, 1);
        var bookDto = bookService.insert("test book", authorId, genreId).block();

        var commentDto1 = commentService.insert("test1", bookDto.id()).block();
        var commentDto2 = commentService.insert("test2", bookDto.id()).block();

        var actualComments = commentService.findByBookId(bookDto.id());

        StepVerifier.create(actualComments)
                .expectNextSequence(List.of(commentDto1, commentDto2))
                .expectComplete()
                .verify();

        bookService.deleteById(bookDto.id()).block();

        StepVerifier.create(commentService.findByBookId(bookDto.id()))
                .expectComplete()
                .verify();
        StepVerifier.create(commentService.findById(commentDto1.id()))
                .expectComplete()
                .verify();
        StepVerifier.create(commentService.findById(commentDto2.id()))
                .expectComplete()
                .verify();
    }
}
