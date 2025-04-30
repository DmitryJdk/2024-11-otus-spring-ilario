package ru.otus.hw.batch.job.writers;

import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.nosql.model.NoSqlAuthor;
import ru.otus.hw.nosql.model.NoSqlBook;
import ru.otus.hw.nosql.model.NoSqlGenre;

@Configuration
public class MongoItemWriterConfiguration {

    @Bean
    public MongoItemWriter<NoSqlBook> mongoBookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<NoSqlBook>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

    @Bean
    public MongoItemWriter<NoSqlAuthor> mongoAuthorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<NoSqlAuthor>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @Bean
    public MongoItemWriter<NoSqlGenre> mongoGenreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<NoSqlGenre>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }
}
