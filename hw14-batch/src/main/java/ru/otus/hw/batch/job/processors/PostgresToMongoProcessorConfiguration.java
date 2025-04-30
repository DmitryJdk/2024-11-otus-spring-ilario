package ru.otus.hw.batch.job.processors;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.batch.job.MigrationCache;
import ru.otus.hw.nosql.model.NoSqlAuthor;
import ru.otus.hw.nosql.model.NoSqlBook;
import ru.otus.hw.nosql.model.NoSqlGenre;
import ru.otus.hw.sql.model.Author;
import ru.otus.hw.sql.model.Book;
import ru.otus.hw.sql.model.Genre;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class PostgresToMongoProcessorConfiguration {

    private final MigrationCache migrationCache;

    @Bean
    public ItemProcessor<Author, NoSqlAuthor> authorMigrationProcessor() {
        return item -> {
            NoSqlAuthor noSqlAuthor = new NoSqlAuthor();
            noSqlAuthor.setId(ObjectId.get().toString());
            noSqlAuthor.setFullName(item.getFullName());
            migrationCache.saveAuthor(item.getId(), noSqlAuthor.getId());
            return noSqlAuthor;
        };
    }

    @Bean
    public ItemProcessor<Genre, NoSqlGenre> genreMigrationProcessor() {
        return item -> {
            NoSqlGenre noSqlGenre = new NoSqlGenre();
            noSqlGenre.setId(ObjectId.get().toString());
            noSqlGenre.setName(item.getName());
            migrationCache.saveGenre(item.getId(), noSqlGenre.getId());
            return noSqlGenre;
        };
    }

    @Bean
    public ItemProcessor<Book, NoSqlBook> bookMigrationProcessor() {
        return item -> {

            NoSqlAuthor noSqlAuthor = new NoSqlAuthor();
            noSqlAuthor.setId(migrationCache.getAuthorId(item.getAuthor().getId()));
            noSqlAuthor.setFullName(item.getAuthor().getFullName());

            var genres = new HashSet<NoSqlGenre>();
            for (Genre g: item.getGenres()) {
                var id = migrationCache.getGenreId(g.getId());
                genres.add(new NoSqlGenre(id, g.getName()));
            }

            NoSqlBook noSqlBook = new NoSqlBook();
            noSqlBook.setTitle(item.getTitle());
            noSqlBook.setNoSqlAuthor(noSqlAuthor);
            noSqlBook.setNoSqlGenres(genres);
            return noSqlBook;
        };
    }
}
