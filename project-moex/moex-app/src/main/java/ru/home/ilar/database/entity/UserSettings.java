package ru.home.ilar.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import ru.home.ilar.database.model.SettingsItem;

import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "USER_SETTINGS", schema = "moex")
public class UserSettings {

    @Id
    private Long id;

    private Long userId;

    private List<SettingsItem> settings;

    public UserSettings() {
        this.settings = new ArrayList<>();
    }

    public UserSettings(Long userId) {
        this.userId = userId;
    }
}
