package ru.otus.hw.dto;

import lombok.Getter;

public record AuthorDto(@Getter Long id, String fullName) { }
