package me.craftinators.harbor.configuration;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Contains methods for fetching resources and files.
 */
public final class ResourceManager {
    private final JavaPlugin plugin;

    public ResourceManager(final @NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Extracts a resource file from the plugin's data folder. If the file doesn't exist, it is copied from the plugin's resources.
     * @param name The name of the resource file to extract
     * @return The extracted resource file
     */
    public @NotNull File extractResource(final @NotNull String name) {
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) plugin.saveResource(name, false);
        return file;
    }
}