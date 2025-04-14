package ru.otus.hw.batch.job.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.batch.job.MigrationCache;
import ru.otus.hw.sql.model.Author;
import ru.otus.hw.sql.model.Book;
import ru.otus.hw.sql.model.Genre;

import java.util.HashSet;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class PostgresToMongoProcessorConfiguration {

    private final MigrationCache migrationCache;

    @Bean
    public ItemProcessor<Author, ru.otus.hw.nosql.model.Author> authorMigrationProcessor() {
        return item -> {
            ru.otus.hw.nosql.model.Author author = new ru.otus.hw.nosql.model.Author();
            author.setId(UUID.randomUUID().toString());
            author.setFullName(item.getFullName());
            migrationCache.saveAuthor(item.getId(), author.getId());
            return author;
        };
    }

    @Bean
    public ItemProcessor<Genre, ru.otus.hw.nosql.model.Genre> genreMigrationProcessor() {
        return item -> {
            ru.otus.hw.nosql.model.Genre genre = new ru.otus.hw.nosql.model.Genre();
            genre.setId(UUID.randomUUID().toString());
            genre.setName(item.getName());
            migrationCache.saveGenre(item.getId(), genre.getId());
            return genre;
        };
    }

    @Bean
    public ItemProcessor<Book, ru.otus.hw.nosql.model.Book> bookMigrationProcessor() {
        return item -> {

            ru.otus.hw.nosql.model.Author author = new ru.otus.hw.nosql.model.Author();
            author.setId(migrationCache.getAuthorId(item.getAuthor().getId()));
            author.setFullName(item.getAuthor().getFullName());

            var genres = new HashSet<ru.otus.hw.nosql.model.Genre>();
            for (Genre g: item.getGenres()) {
                var id = migrationCache.getGenreId(g.getId());
                genres.add(new ru.otus.hw.nosql.model.Genre(id, g.getName()));
            }

            ru.otus.hw.nosql.model.Book book = new ru.otus.hw.nosql.model.Book();
            book.setTitle(item.getTitle());
            book.setAuthor(author);
            book.setGenres(genres);
            return book;
        };
    }
}
