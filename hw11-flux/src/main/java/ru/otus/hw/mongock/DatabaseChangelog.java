package ru.otus.hw.mongock;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {

    private final List<Author> authors = new ArrayList<>();

    private final List<Genre> genres = new ArrayList<>();

    private final List<Book> books = new ArrayList<>();

    @ChangeSet(order = "001", id = "init", author = "dmitryJdk", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "authors", author = "dmitryJdk", runAlways = true)
    public void insertAuthors(AuthorRepository authorRepository) {
        Flux<Author> authors = Flux.just(
                new Author("Author_1"),
                new Author("Author_2"),
                new Author("Author_3")
        );
        authorRepository.insert(authors)
                .doOnNext(this.authors::add)
                .blockLast();
    }

    @ChangeSet(order = "003", id = "genres", author = "dmitryJdk", runAlways = true)
    public void insertGenres(GenreRepository genreRepository) {
        Flux<Genre> genres = Flux.just(
                new Genre("Genre_1"),
                new Genre("Genre_2"),
                new Genre("Genre_3"),
                new Genre("Genre_4"),
                new Genre("Genre_5"),
                new Genre("Genre_6")
        );
        genreRepository.insert(genres)
                .doOnNext(this.genres::add)
                .blockLast();
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "dmitryJdk", runAlways = true)
    public void insertBooks(BookRepository bookRepository) {
        Flux<Book> books = Flux.just(
                new Book("Book_1", authors.get(0),
                        Set.of(genres.get(0), genres.get(1))),
                new Book("Book_2", authors.get(1),
                        Set.of(genres.get(2), genres.get(3))),
                new Book("Book_3", authors.get(2),
                        Set.of(genres.get(4), genres.get(5)))
        );
        bookRepository.insert(books)
                .doOnNext(this.books::add)
                .blockLast();
    }

    @ChangeSet(order = "005", id = "insertComments", author = "dmitryJdk", runAlways = true)
    public void insertComments(CommentRepository commentRepository) {
        List<Comment> comments = new ArrayList<>();
        books.forEach(book ->
                comments.addAll(List.of(
                                new Comment("Comment_1", book),
                                new Comment("Comment_2", book)
                        )
                )
        );
        commentRepository.insert(comments).blockLast();
    }

}
