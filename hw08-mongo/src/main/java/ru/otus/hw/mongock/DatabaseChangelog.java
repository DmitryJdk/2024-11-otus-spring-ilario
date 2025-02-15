package ru.otus.hw.mongock;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {

    private List<Book> books;

    @ChangeSet(order = "001", id = "init", author = "dmitryJdk", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBooks", author = "dmitryJdk", runAlways = true)
    public void insertBooks(BookRepository repository) {
        List<Book> books = List.of(
                new Book("Book_1", new Author("Author_1"),
                        Set.of(new Genre("Genre_1"), new Genre("Genre_2"))),
                new Book("Book_2", new Author("Author_2"),
                        Set.of(new Genre("Genre_3"), new Genre("Genre_4"))),
                new Book("Book_3", new Author("Author_3"),
                        Set.of(new Genre("Genre_5"), new Genre("Genre_6")))
        );
        this.books = repository.insert(books);
    }

    @ChangeSet(order = "003", id = "insertComments", author = "dmitryJdk", runAlways = true)
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
