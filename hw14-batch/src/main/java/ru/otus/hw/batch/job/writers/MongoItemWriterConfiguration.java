package ru.otus.hw.batch.job.writers;

import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.nosql.model.Author;
import ru.otus.hw.nosql.model.Book;
import ru.otus.hw.nosql.model.Genre;

@Configuration
public class MongoItemWriterConfiguration {

    @Bean
    public MongoItemWriter<Book> mongoBookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Book>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

    @Bean
    public MongoItemWriter<Author> mongoAuthorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Author>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @Bean
    public MongoItemWriter<Genre> mongoGenreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Genre>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }
}
