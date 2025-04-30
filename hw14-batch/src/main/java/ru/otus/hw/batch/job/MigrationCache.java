package ru.otus.hw.batch.job;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MigrationCache {

    private final Map<Long, String> authorsCache = new ConcurrentHashMap<>();

    private final Map<Long, String> genresCache = new ConcurrentHashMap<>();

    public void init() {
        authorsCache.clear();
        genresCache.clear();
    }

    public void saveAuthor(Long key, String value) {
        authorsCache.put(key, value);
    }

    public void saveGenre(long id, String value) {
        genresCache.put(id, value);
    }

    public String getAuthorId(long id) {
        return authorsCache.get(id);
    }

    public String getGenreId(long id) {
        return genresCache.get(id);
    }
}
