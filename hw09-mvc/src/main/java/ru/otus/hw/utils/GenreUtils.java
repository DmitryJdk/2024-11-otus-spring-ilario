package ru.otus.hw.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreUtils {

    public static Set<String> parseGenres(String genres) {
        return Arrays.stream(genres.split(","))
                .map(String::strip)
                .collect(Collectors.toSet());
    }
}
