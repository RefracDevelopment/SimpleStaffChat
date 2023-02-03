package me.refracdevelopment.simplestaffchat.spigot.utilities.config;

import me.refracdevelopment.simplestaffchat.spigot.manager.ConfigurationManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;

import java.util.List;

public class Config {
    // General
    public static String STAFFCHAT_SYMBOL;

    // Format
    public static String MINECRAFT_FORMAT;
    public static String CONSOLE_FORMAT;
    public static boolean JOIN_ENABLED;
    public static String JOIN_FORMAT;
    public static String JOIN_QUIT_FORMAT;

    // Messages
    public static String STAFFCHAT_OUTPUT;
    public static List<String> STAFFCHAT_MESSAGE;

    // Settings
    public static boolean VELOCITY;

    public static void loadConfig() {
        // General
        STAFFCHAT_SYMBOL = ConfigurationManager.Setting.STAFFCHAT_SYMBOL.getString();

        // Format
        MINECRAFT_FORMAT = ConfigurationManager.Setting.MINECRAFT_FORMAT.getString();
        CONSOLE_FORMAT = ConfigurationManager.Setting.CONSOLE_FORMAT.getString();

        JOIN_ENABLED = ConfigurationManager.Setting.JOIN_ENABLED.getBoolean();
        JOIN_FORMAT = ConfigurationManager.Setting.JOIN_FORMAT.getString();
        JOIN_QUIT_FORMAT = ConfigurationManager.Setting.JOIN_QUIT_FORMAT.getString();

        // Messages
        STAFFCHAT_OUTPUT = ConfigurationManager.Setting.STAFFCHAT_OUTPUT.getString();
        STAFFCHAT_MESSAGE = ConfigurationManager.Setting.STAFFCHAT_MESSAGE.getStringList();

        // Settings
        VELOCITY = ConfigurationManager.Setting.VELOCITY.getBoolean();

        Color.log("&c==========================================");
        Color.log("&aAll files have been loaded correctly!");
        Color.log("&c==========================================");
    }
}