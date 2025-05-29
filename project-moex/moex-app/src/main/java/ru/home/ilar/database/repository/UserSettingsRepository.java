package ru.home.ilar.database.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.home.ilar.database.entity.UserSettings;

public interface UserSettingsRepository extends ReactiveCrudRepository<UserSettings, Long> {

    Mono<UserSettings> findByUserId(Long userId);

}
