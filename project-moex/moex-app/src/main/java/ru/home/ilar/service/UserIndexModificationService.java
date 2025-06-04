package ru.home.ilar.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.home.ilar.database.entity.UserSettings;
import ru.home.ilar.dto.UserIndexModificationItemDto;

import java.util.List;

public interface UserIndexModificationService {

    Flux<UserIndexModificationItemDto> getUserIndexModifications(Long userId);

    Mono<UserSettings> saveUserIndexModifications(Long userId, List<UserIndexModificationItemDto> userSettingsItems);
}
