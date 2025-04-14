package ru.otus.hw.sql.mapper;

import org.mapstruct.Mapper;
import ru.otus.hw.sql.dto.AuthorDto;
import ru.otus.hw.sql.dto.BookDto;
import ru.otus.hw.sql.dto.GenreDto;
import ru.otus.hw.sql.model.Author;
import ru.otus.hw.sql.model.Book;
import ru.otus.hw.sql.model.Genre;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    BookDto bookToBookDto(Book book);

    AuthorDto authorToAuthorDto(Author author);

    GenreDto genreToGenreDto(Genre genre);

}
