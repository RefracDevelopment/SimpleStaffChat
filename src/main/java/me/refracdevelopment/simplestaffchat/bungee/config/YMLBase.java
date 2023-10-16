package me.refracdevelopment.simplestaffchat.bungee.config;

import lombok.Getter;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Taken from
 * https://github.com/IllusionTheDev/SkyblockCore/blob/master/src/main/java/me/illusion/skyblockcore/bungee/utilities/YMLBase.java
 * Modified for my needs
 */
@Getter
public class YMLBase {

    private final BungeeStaffChat plugin;
    private final File file;
    private Configuration configuration;

    public YMLBase(BungeeStaffChat plugin, String name) {
        this(plugin, new File(plugin.getDataFolder(), name), true);
    }

    public YMLBase(BungeeStaffChat plugin, File file, boolean existsOnSource) {
        this.file = file;
        this.plugin = plugin;

        if (!file.exists()) {
            try (InputStream stream = plugin.getResourceAsStream(file.getName())) {
                file.getParentFile().mkdirs();
                if (existsOnSource) {
                    Files.copy(stream, file.toPath());
                    stream.close();
                } else
                    file.createNewFile();
            } catch (IOException e) {
                plugin.getColor().log(e.getMessage());
            }
        }

        load();
    }

    public void load() {
        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            plugin.getColor().log(e.getMessage());
        }
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            plugin.getColor().log(e.getMessage());
        }
    }

    public int getInt(String path) {
        return configuration.getInt(path, 0);
    }

    public double getDouble(String path) {
        return configuration.getDouble(path, 0.0);
    }

    public boolean getBoolean(String path) {
        return configuration.getBoolean(path, false);
    }

    public String getString(String path, boolean check) {
        return configuration.getString(path, null);
    }

    public String getString(String path) {
        if (configuration.contains(path)) {
            return ChatColor.translateAlternateColorCodes('&', configuration.getString(path, "String at path '" + path + "' not found.")).replace("|", "\u2503");
        }

        return null;
    }

    public List<String> getStringList(String path) {
        return configuration.getStringList(path).stream().map(this.plugin.getColor()::translate).collect(Collectors.toList());
    }

    public List<String> getStringList(String path, boolean check) {
        if (!configuration.contains(path)) return null;
        return configuration.getStringList(path).stream().map(this.plugin.getColor()::translate).collect(Collectors.toList());
    }

}