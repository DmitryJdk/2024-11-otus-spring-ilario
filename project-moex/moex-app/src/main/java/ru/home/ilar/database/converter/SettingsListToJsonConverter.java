package ru.home.ilar.database.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import jakarta.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import ru.home.ilar.database.model.SettingsItem;

import java.util.List;

@WritingConverter
public class SettingsListToJsonConverter implements Converter<List<SettingsItem>, Json> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Json convert(@Nonnull List<SettingsItem> source) {
        try {
            String json = objectMapper.writeValueAsString(source);
            return Json.of(json);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing List<SettingsItem> to JSON", e);
        }
    }
}
