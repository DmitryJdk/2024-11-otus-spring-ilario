package ru.home.ilar.mapper;

import org.mapstruct.Mapper;
import ru.home.ilar.dto.UserSettingsItemDto;
import ru.home.ilar.database.model.SettingsItem;

@Mapper(componentModel = "spring")
public interface UserSettingsMapper {

    UserSettingsItemDto mapToDto(SettingsItem settings);

    SettingsItem mapToEntity(UserSettingsItemDto settingsDto);
}
