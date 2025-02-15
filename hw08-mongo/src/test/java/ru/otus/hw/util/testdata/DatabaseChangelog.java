package ru.otus.hw.util.testdata;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.util.TestDataUtil;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "init", author = "dmitryJdk", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBooks", author = "dmitryJdk", runAlways = true)
    public void insertBooks(BookRepository repository) {
        repository.insert(TestDataUtil.books);
    }

    @ChangeSet(order = "003", id = "insertComments", author = "dmitryJdk", runAlways = true)
    public void insertComments(CommentRepository commentRepository) {
        commentRepository.insert(TestDataUtil.comments);
    }

}
