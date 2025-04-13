package ru.otus.hw.dto;

import java.util.Set;

public record BookRequest(String title, Long authorId, Set<Long> genresIds) {}
