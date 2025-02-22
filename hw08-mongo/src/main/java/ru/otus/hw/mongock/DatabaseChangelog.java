package ru.otus.hw.mongock;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
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

    private List<Author> authors;

    private List<Genre> genres;

    private List<Book> books;

    @ChangeSet(order = "001", id = "init", author = "dmitryJdk", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "authors", author = "dmitryJdk", runAlways = true)
    public void insertAuthors(AuthorRepository authorRepository) {
        List<Author> authors = List.of(
                new Author("Author_1"),
                new Author("Author_2"),
                new Author("Author_3")
        );
        this.authors = authorRepository.insert(authors);
    }

    @ChangeSet(order = "003", id = "genres", author = "dmitryJdk", runAlways = true)
    public void insertGenres(GenreRepository genreRepository) {
        List<Genre> genres = List.of(
                new Genre("Genre_1"),
                new Genre("Genre_2"),
                new Genre("Genre_3"),
                new Genre("Genre_4"),
                new Genre("Genre_5"),
                new Genre("Genre_6")
        );
        this.genres = genreRepository.insert(genres);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "dmitryJdk", runAlways = true)
    public void insertBooks(BookRepository bookRepository) {
        List<Book> books = List.of(
                new Book("Book_1", authors.get(0),
                        Set.of(genres.get(0), genres.get(1))),
                new Book("Book_2", authors.get(1),
                        Set.of(genres.get(2), genres.get(3))),
                new Book("Book_3", authors.get(2),
                        Set.of(genres.get(4), genres.get(5)))
        );
        this.books = bookRepository.insert(books);
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
        commentRepository.insert(comments);
    }

}
