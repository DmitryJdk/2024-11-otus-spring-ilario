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
                                ItemProcessor<Author, ru.otus.hw.nosql.model.Author> authorMigrationProcessor,
                                MongoItemWriter<ru.otus.hw.nosql.model.Author> mongoAuthorWriter) {

        return new StepBuilder("authorMigration", jobRepository)
                .<Author, ru.otus.hw.nosql.model.Author>chunk(1, transactionManager)
                .reader(reader)
                .processor(authorMigrationProcessor)
                .writer(mongoAuthorWriter)
                .build();
    }

    @Bean
    public Step genreMigration(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                JpaPagingItemReader<Genre> reader,
                                ItemProcessor<Genre, ru.otus.hw.nosql.model.Genre> genreMigrationProcessor,
                                MongoItemWriter<ru.otus.hw.nosql.model.Genre> mongoGenreWriter) {

        return new StepBuilder("genreMigration", jobRepository)
                .<Genre, ru.otus.hw.nosql.model.Genre>chunk(1, transactionManager)
                .reader(reader)
                .processor(genreMigrationProcessor)
                .writer(mongoGenreWriter)
                .build();
    }

    @Bean
    public Step bookMigration(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               JpaPagingItemReader<Book> reader,
                               ItemProcessor<Book, ru.otus.hw.nosql.model.Book> bookMigrationProcessor,
                               MongoItemWriter<ru.otus.hw.nosql.model.Book> mongoBookWriter) {

        return new StepBuilder("bookMigration", jobRepository)
                .<Book, ru.otus.hw.nosql.model.Book>chunk(1, transactionManager)
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
