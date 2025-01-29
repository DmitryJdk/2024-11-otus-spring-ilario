package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;

@Component
@RequiredArgsConstructor
public class CommentConverter {

    private final BookConverter bookConverter;

    public String commentToString(CommentDto comment) {
        var bookString = bookConverter.bookToString(comment.book());
        return "Id: " + comment.id() + ", text: " + comment.text()
                + ", book: {" + bookString + "}";
    }
}
