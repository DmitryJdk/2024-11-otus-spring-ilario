package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final GenreRepository genreRepository;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        var params = Map.of("id", id);
        var book = namedParameterJdbcOperations.query(
                "select b.id, b.title, b.author_id, au.full_name, bg.genre_id, gen.name "
                        + "from books b "
                        + "left join books_genres bg on b.id=bg.book_id "
                        + "left join genres gen on bg.genre_id=gen.id "
                        + "left join authors au on b.author_id=au.id "
                        + "where b.id=:id",
                params,
                new BookResultSetExtractor()
        );
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id",
                params
        );
    }

    private List<Book> getAllBooksWithoutGenres() {
        return namedParameterJdbcOperations.query(
                "select b.id, b.title, b.author_id, au.full_name from books b "
                        + "left join authors au on b.author_id = au.id ",
                new BookRowMapper()
        );
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return namedParameterJdbcOperations.query(
                "select book_id, genre_id from books_genres",
                new BookGenreRowMapper()
        );
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {

        var relationsGroupingWithGenres = relations.stream()
                .collect(Collectors.groupingBy(
                        relation -> relation.bookId,
                        Collectors.mapping(relation -> getGenreById(genres, relation.genreId),
                                Collectors.toList())
                        )
                );
       booksWithoutGenres.forEach(book -> {
           book.setGenres(relationsGroupingWithGenres.get(book.getId()));
       });
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of(
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId())
        );
        namedParameterJdbcOperations.update(
                "insert into books(title, author_id) values (:title, :authorId)",
                params,
                keyHolder
        );
        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        var params = Map.of("id", book.getId(),
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId());
        var rowsAffected = namedParameterJdbcOperations.update(
                "update books set title=:title, author_id=:authorId where id=:id",
                params
        );
        if (rowsAffected == 0) {
            throw new EntityNotFoundException("Nothing to update");
        }
        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        var relations = book.getGenres().stream()
                .map(genre -> new BookGenreRelation(book.getId(), genre.getId()))
                .toList();
        namedParameterJdbcOperations.batchUpdate(
                "insert into books_genres(book_id, genre_id) values (:bookId, :genreId)",
                SqlParameterSourceUtils.createBatch(relations)
        );
    }

    private void removeGenresRelationsFor(Book book) {
        var params = Map.of("bookId", book.getId());
        namedParameterJdbcOperations.update(
                "delete from books_genres where book_id=:bookId",
                params
        );
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("id");
            var title = rs.getString("title");
            var authorId = rs.getLong("author_id");
            var authorFullName = rs.getString("full_name");
            return new Book(id, title, new Author(authorId, authorFullName), List.of());
        }
    }

    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            Book book = null;
            while (rs.next()) {
                if (book == null) {
                    var id = rs.getLong("id");
                    var title = rs.getString("title");
                    var authorId = rs.getLong("author_id");
                    var authorFullName = rs.getString("full_name");
                    book = new Book(id, title, new Author(authorId, authorFullName), new ArrayList<>());
                }
                var genreId = rs.getLong("genre_id");
                var genreName = rs.getString("name");
                book.getGenres().add(new Genre(genreId, genreName));
            }
            return book;
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }

    private static class BookGenreRowMapper implements RowMapper<BookGenreRelation> {

        @Override
        public BookGenreRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            var bookId = rs.getLong("book_id");
            var genreId = rs.getLong("genre_id");
            return new BookGenreRelation(bookId, genreId);
        }
    }

    private Genre getGenreById(List<Genre> genres, long id) {
        return genres.stream()
                .filter(genre -> genre.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
