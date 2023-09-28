package me.refracdevelopment.simplestaffchat.spigot.config;

import me.refracdevelopment.simplestaffchat.spigot.manager.ConfigurationManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;

import java.util.List;

public class Config {
    // General
    public static String STAFFCHAT_SYMBOL;
    public static String ADMINCHAT_SYMBOL;
    public static String DEVCHAT_SYMBOL;

    // Format
    public static String MINECRAFT_FORMAT;
    public static String CONSOLE_FORMAT;
    public static String ADMINCHAT_FORMAT;
    public static String CONSOLE_ADMINCHAT_FORMAT;
    public static String DEVCHAT_FORMAT;
    public static String CONSOLE_DEVCHAT_FORMAT;

    // Join
    public static boolean JOIN_ENABLED;
    public static String JOIN_FORMAT;
    public static String JOIN_QUIT_FORMAT;

    // Messages
    public static String STAFFCHAT_OUTPUT;
    public static List<String> STAFFCHAT_MESSAGE;

    // Settings
    public static boolean BUNGEECORD;
    public static String SERVER_NAME;
    public static boolean SYMBOLS;

    public static void loadConfig() {
        // General
        STAFFCHAT_SYMBOL = ConfigurationManager.Setting.STAFFCHAT_SYMBOL.getString();
        ADMINCHAT_SYMBOL = ConfigurationManager.Setting.ADMINCHAT_SYMBOL.getString();
        DEVCHAT_SYMBOL = ConfigurationManager.Setting.DEVCHAT_SYMBOL.getString();

        // Format
        MINECRAFT_FORMAT = ConfigurationManager.Setting.MINECRAFT_FORMAT.getString();
        CONSOLE_FORMAT = ConfigurationManager.Setting.CONSOLE_FORMAT.getString();
        ADMINCHAT_FORMAT = ConfigurationManager.Setting.ADMINCHAT_FORMAT.getString();
        CONSOLE_ADMINCHAT_FORMAT = ConfigurationManager.Setting.CONSOLE_ADMINCHAT_FORMAT.getString();
        DEVCHAT_FORMAT = ConfigurationManager.Setting.DEVCHAT_FORMAT.getString();
        CONSOLE_DEVCHAT_FORMAT = ConfigurationManager.Setting.CONSOLE_DEVCHAT_FORMAT.getString();

        // Join
        JOIN_ENABLED = ConfigurationManager.Setting.JOIN_ENABLED.getBoolean();
        JOIN_FORMAT = ConfigurationManager.Setting.JOIN_FORMAT.getString();
        JOIN_QUIT_FORMAT = ConfigurationManager.Setting.JOIN_QUIT_FORMAT.getString();

        // Messages
        STAFFCHAT_OUTPUT = ConfigurationManager.Setting.STAFFCHAT_OUTPUT.getString();
        STAFFCHAT_MESSAGE = ConfigurationManager.Setting.STAFFCHAT_MESSAGE.getStringList();

        // Settings
        BUNGEECORD = ConfigurationManager.Setting.BUNGEECORD.getBoolean();
        SERVER_NAME = ConfigurationManager.Setting.SERVER_NAME.getString();
        SYMBOLS = ConfigurationManager.Setting.SYMBOLS.getBoolean();

        Color.log("&c==========================================");
        Color.log("&aAll files have been loaded correctly!");
        Color.log("&c==========================================");
    }
}