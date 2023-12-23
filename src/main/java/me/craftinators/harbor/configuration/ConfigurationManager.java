package me.craftinators.harbor.configuration;

import me.craftinators.harbor.HarborPlugin;
import me.craftinators.harbor.utilities.AdventureAPIUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class ConfigurationManager {
    private final YamlConfiguration defaultMessagesConfiguration;
    private YamlConfiguration messagesConfiguration;
    private final HarborPlugin plugin;

    public ConfigurationManager(final @NotNull HarborPlugin plugin) {
        this.plugin = plugin;
        defaultMessagesConfiguration = extractDefault("messages.yml");
    }

    public void validateConfiguration() {
        messagesConfiguration = validateResource("messages.yml", defaultMessagesConfiguration);
    }

    @SuppressWarnings("SameParameterValue")
    private @Nullable YamlConfiguration extractDefault(final @NotNull String source) {
        InputStream is = plugin.getResource(source);
        if (is == null) return null;

        try (InputStreamReader isr = new InputStreamReader(is)) {
            return YamlConfiguration.loadConfiguration(isr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private @NotNull YamlConfiguration validateResource(final @NotNull String fileName, final @NotNull YamlConfiguration defaultConfiguration) {
        File configurationFile = plugin.getResourceManager().extractResource(fileName);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(configurationFile);
        boolean updated = false;
        for (String key : defaultConfiguration.getKeys(true)) {
            if (configuration.get(key) != null) continue;
            updated = true;
            Message.UPDATED_OPTION.log(AdventureAPIUtil.resolve("path", key), AdventureAPIUtil.resolve("file", fileName));
            configuration.set(key, defaultConfiguration.get(key));
        }
        if (updated) {
            try {
                configuration.save(configurationFile);
            } catch (IOException e) {
                Message.SAVE_ERROR.log(AdventureAPIUtil.resolve("file", fileName));
            }
        }
        return configuration;
    }

    public @NotNull YamlConfiguration getMessagesConfiguration() {
        return messagesConfiguration == null ? defaultMessagesConfiguration : messagesConfiguration;
    }
}
