package me.refrac.simplestaffchat.spigot.utilities;

import me.refrac.simplestaffchat.spigot.SimpleStaffChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Files {
    private static File configFile;
    private static FileConfiguration config;

    private static File messagesFile;
    private static FileConfiguration messages;

    private static File menusFile;
    private static FileConfiguration menus;

    public static void loadFiles(SimpleStaffChat staffChat) {
        if (!staffChat.getDataFolder().exists()) {
            staffChat.getDataFolder().mkdirs();
        }

        configFile = new File(staffChat.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            staffChat.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        messagesFile = new File(staffChat.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            staffChat.saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);

        menusFile = new File(staffChat.getDataFolder(), "menus.yml");
        if (!menusFile.exists()) {
            staffChat.saveResource("menus.yml", false);
        }
        menus = YamlConfiguration.loadConfiguration(menusFile);
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static FileConfiguration getMessages() {
        return messages;
    }

    public static FileConfiguration getMenus() {
        return menus;
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

    public static void reloadMessages(SimpleStaffChat staffChat) {
        messagesFile = new File(staffChat.getDataFolder(), "messages.yml");
        try {
            messages = YamlConfiguration.loadConfiguration(messagesFile);
        } catch (Exception e) {
            Logger.ERROR.out("Failed to reload the messages file!");
            e.printStackTrace();
        }
    }
}