package ru.home.ilar.database.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import jakarta.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import ru.home.ilar.database.model.SettingsItem;

import java.util.List;

@ReadingConverter
public class JsonToSettingsListConverter implements Converter<Json, List<SettingsItem>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<SettingsItem> convert(@Nonnull Json source) {
        try {
            return objectMapper.readValue(source.asString(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON to List<SettingsItem>", e);
        }
    }
}
