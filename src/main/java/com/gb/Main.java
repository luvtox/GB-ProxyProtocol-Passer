package com.gb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {

    private boolean enabled;

    @Override
    public void onEnable() {
        // Load configuration
        loadConfig();

        // Register event listener
        getProxy().getPluginManager().registerListener(this, new PassProxyListener(this));

        getLogger().info("GB-PROXY-PASSER by luvtox has been enabled");
    }

    @Override
    public void onDisable() {
        // Nothing to do here
    }

    private void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            // Create default configuration file
            configFile.getParentFile().mkdirs();
            try (InputStream is = getResourceAsStream("config.yml")) {
                Files.copy(is, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            enabled = config.getBoolean("enabled", true);
        } catch (IOException e) {
            getLogger().log(Level.WARNING, "Failed to load config.yml", e);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }
}
