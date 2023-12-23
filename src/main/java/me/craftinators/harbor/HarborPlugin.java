package me.craftinators.harbor;

import me.craftinators.harbor.configuration.ConfigurationManager;
import me.craftinators.harbor.configuration.ResourceManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.UnknownNullability;

public final class HarborPlugin extends JavaPlugin {
    private static HarborPlugin instance = null;
    private ResourceManager resourceManager;
    private ConfigurationManager configurationManager;

    @Override
    public void onEnable() {
        instance = this;

        resourceManager = new ResourceManager(this);
        configurationManager = new ConfigurationManager(this);

        configurationManager.validateConfiguration();
    }

    @Override
    public void onDisable() {

    }

    public static @UnknownNullability HarborPlugin get() {
        return instance;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }
}
