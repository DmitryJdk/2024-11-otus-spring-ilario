package ru.home.ilar.mapper;

import org.mapstruct.Mapper;
import ru.home.ilar.dto.UserIndexModificationItemDto;
import ru.home.ilar.database.model.SettingsItem;

@Mapper(componentModel = "spring")
public interface UserSettingsMapper {

    UserIndexModificationItemDto mapToDto(SettingsItem settings);

    SettingsItem mapToEntity(UserIndexModificationItemDto settingsDto);
}
