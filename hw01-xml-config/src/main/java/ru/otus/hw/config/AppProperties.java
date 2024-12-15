package ru.otus.hw.config;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AppProperties implements TestFileNameProvider {
    private String testFileName;

    @Override
    public String getTestFileName() {
        return testFileName;
    }
}
