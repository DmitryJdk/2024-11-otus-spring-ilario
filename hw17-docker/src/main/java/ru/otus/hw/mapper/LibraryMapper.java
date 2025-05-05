package ru.otus.hw.mapper;

import org.mapstruct.Mapper;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    BookDto bookToBookDto(Book book);

    AuthorDto authorToAuthorDto(Author author);

    GenreDto genreToGenreDto(Genre genre);

    CommentDto commentToCommentDto(Comment comment);

    default String genreToString(Genre genre) {
        return genre == null ? null : genre.getName();
    }

}
