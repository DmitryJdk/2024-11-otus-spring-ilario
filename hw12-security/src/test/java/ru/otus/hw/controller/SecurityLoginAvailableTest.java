package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest()
public class SecurityLoginAvailableTest {

    @Configuration
    @ComponentScan("ru.otus.hw.security.configuration")
    static class EmptyConfiguration {}

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("должна быть доступна страница авторизации")
    void loginShouldBeAvailable() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}
