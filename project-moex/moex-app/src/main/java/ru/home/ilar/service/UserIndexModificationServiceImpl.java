package ru.home.ilar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.home.ilar.dto.UserIndexModificationItemDto;
import ru.home.ilar.database.entity.UserSettings;
import ru.home.ilar.mapper.UserSettingsMapper;
import ru.home.ilar.database.model.SettingsItem;
import ru.home.ilar.database.repository.UserSettingsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserIndexModificationServiceImpl implements UserIndexModificationService {

    private final UserSettingsRepository userSettingsRepository;

    private final UserSettingsMapper userSettingsMapper;

    @Override
    @Transactional(readOnly = true)
    public Flux<UserIndexModificationItemDto> getUserIndexModifications(Long userId) {
        return userSettingsRepository.findByUserId(userId)
                .switchIfEmpty(Mono.just(new UserSettings()))
                .map(UserSettings::getSettings)
                .flatMapMany(Flux::fromIterable)
                .map(userSettingsMapper::mapToDto);
    }

    @Override
    @Transactional
    public Mono<UserSettings> saveUserIndexModifications(Long userId,
                                                         List<UserIndexModificationItemDto> userSettingsItems) {
        List<SettingsItem> settings = userSettingsItems.stream()
                .map(userSettingsMapper::mapToEntity)
                .toList();
        return userSettingsRepository.findByUserId(userId)
                .switchIfEmpty(Mono.just(new UserSettings(userId)))
                .flatMap(us -> {
                    us.setSettings(settings);
                    return userSettingsRepository.save(us);
                });
    }
}
