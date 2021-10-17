/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot.utilities;

import me.refrac.simplestaffchat.spigot.SimpleStaffChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Files {
    private static File configFile;
    private static FileConfiguration config;

    public static void loadFiles(SimpleStaffChat staffChat) {
        if (!staffChat.getDataFolder().exists()) {
            staffChat.getDataFolder().mkdirs();
        }

        configFile = new File(staffChat.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            staffChat.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void reloadConfig(SimpleStaffChat staffChat) {
        configFile = new File(staffChat.getDataFolder(), "config.yml");
        try {
            config = YamlConfiguration.loadConfiguration(configFile);
        } catch (Exception e) {
            Logger.ERROR.out("Failed to reload the config file!");
            e.printStackTrace();
        }
    }
}