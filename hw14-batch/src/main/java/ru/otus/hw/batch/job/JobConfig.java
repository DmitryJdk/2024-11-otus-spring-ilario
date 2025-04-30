package ru.otus.hw.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.nosql.model.NoSqlAuthor;
import ru.otus.hw.nosql.model.NoSqlBook;
import ru.otus.hw.nosql.model.NoSqlGenre;
import ru.otus.hw.sql.model.Author;
import ru.otus.hw.sql.model.Book;
import ru.otus.hw.sql.model.Genre;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    @Bean
    public Step authorMigration(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                JpaPagingItemReader<Author> reader,
                                ItemProcessor<Author, NoSqlAuthor> authorMigrationProcessor,
                                MongoItemWriter<NoSqlAuthor> mongoAuthorWriter) {

        return new StepBuilder("authorMigration", jobRepository)
                .<Author, NoSqlAuthor>chunk(3, transactionManager)
                .reader(reader)
                .processor(authorMigrationProcessor)
                .writer(mongoAuthorWriter)
                .build();
    }

    @Bean
    public Step genreMigration(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                JpaPagingItemReader<Genre> reader,
                                ItemProcessor<Genre, NoSqlGenre> genreMigrationProcessor,
                                MongoItemWriter<NoSqlGenre> mongoGenreWriter) {

        return new StepBuilder("genreMigration", jobRepository)
                .<Genre, NoSqlGenre>chunk(3, transactionManager)
                .reader(reader)
                .processor(genreMigrationProcessor)
                .writer(mongoGenreWriter)
                .build();
    }

    @Bean
    public Step bookMigration(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               JpaPagingItemReader<Book> reader,
                               ItemProcessor<Book, NoSqlBook> bookMigrationProcessor,
                               MongoItemWriter<NoSqlBook> mongoBookWriter) {

        return new StepBuilder("bookMigration", jobRepository)
                .<Book, NoSqlBook>chunk(3, transactionManager)
                .reader(reader)
                .processor(bookMigrationProcessor)
                .writer(mongoBookWriter)
                .build();
    }

    @Bean
    public Job yourJob(JobRepository jobRepository,
                       Step authorMigration,
                       Step genreMigration,
                       Step bookMigration) {
        return new JobBuilder("job", jobRepository)
                .start(authorMigration)
                .next(genreMigration)
                .next(bookMigration)
                .build();
    }
}
