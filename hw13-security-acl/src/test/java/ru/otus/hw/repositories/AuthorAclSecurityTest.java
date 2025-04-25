package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.hw.security.configuration.AclConfig;
import ru.otus.hw.security.configuration.SecurityConfiguration;

@DataJpaTest
@Import({SecurityConfiguration.class, AclConfig.class})
@DisplayName("Проверка ограничений доступа к репозиторию авторов ")
public class AuthorAclSecurityTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @WithAnonymousUser
    @DisplayName("должен вернуть пустой список, если пользователь аноним")
    public void shouldReturnEmptyIfUserIsAnonymous() {
        var authors = authorRepository.findAll();
        Assertions.assertThat(authors).hasSize(0);
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен вернуть всех авторов, если у пользователя есть права")
    public void shouldReturnAllWhenUserHasRights() {
        var authors = authorRepository.findAll();
        Assertions.assertThat(authors).hasSize(3);
    }

    @Test
    @WithMockUser(username = "user1")
    @DisplayName("должен вернуть всех авторов, кроме Author_1, если у пользователя нет прав")
    public void shouldReturnPartIfUserHasNoRights() {
        var authors = authorRepository.findAll();
        Assertions.assertThat(authors).hasSize(2);
    }

}
