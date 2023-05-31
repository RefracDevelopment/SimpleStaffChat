package me.refracdevelopment.simplestaffchat.bungee.config.cache;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;

import java.util.List;

public class Config {

    // General
    public static String STAFFCHAT_SYMBOL;
    public static String ADMINCHAT_SYMBOL;
    public static String DEVCHAT_SYMBOL;
    public static boolean LUCKPERMS;

    // Format
    public static String STAFFCHAT_FORMAT;
    public static String CONSOLE_STAFFCHAT_FORMAT;
    public static String ADMINCHAT_FORMAT;
    public static String CONSOLE_ADMINCHAT_FORMAT;
    public static String DEVCHAT_FORMAT;
    public static String CONSOLE_DEVCHAT_FORMAT;

    // Join
    public static boolean JOIN_ENABLED;
    public static String JOIN_FORMAT;
    public static String SWITCH_FORMAT;
    public static String QUIT_FORMAT;

    // Messages
    public static String PREFIX;
    public static String NO_PERMISSION;
    public static String RELOAD;
    public static String STAFFCHAT_TOGGLE_ON;
    public static String STAFFCHAT_TOGGLE_OFF;
    public static String ADMINCHAT_TOGGLE_ON;
    public static String ADMINCHAT_TOGGLE_OFF;
    public static String DEVCHAT_TOGGLE_ON;
    public static String DEVCHAT_TOGGLE_OFF;
    public static String STAFFCHAT_OUTPUT;
    public static List<String> STAFFCHAT_MESSAGE;

    public static void loadConfig() {
        STAFFCHAT_SYMBOL = BungeeStaffChat.getInstance().getConfigFile().getString("staffchat-symbol");
        ADMINCHAT_SYMBOL = BungeeStaffChat.getInstance().getConfigFile().getString("adminchat-symbol");
        DEVCHAT_SYMBOL = BungeeStaffChat.getInstance().getConfigFile().getString("devchat-symbol");
        LUCKPERMS = BungeeStaffChat.getInstance().getConfigFile().getBoolean("luckperms");

        STAFFCHAT_FORMAT = BungeeStaffChat.getInstance().getConfigFile().getString("format.minecraft-format");
        CONSOLE_STAFFCHAT_FORMAT = BungeeStaffChat.getInstance().getConfigFile().getString("format.console-staffchat-format");
        ADMINCHAT_FORMAT = BungeeStaffChat.getInstance().getConfigFile().getString("format.adminchat-format");
        CONSOLE_ADMINCHAT_FORMAT = BungeeStaffChat.getInstance().getConfigFile().getString("format.console-adminchat-format");
        DEVCHAT_FORMAT = BungeeStaffChat.getInstance().getConfigFile().getString("format.devchat-format");
        CONSOLE_DEVCHAT_FORMAT = BungeeStaffChat.getInstance().getConfigFile().getString("format.console-devchat-format");

        JOIN_ENABLED = BungeeStaffChat.getInstance().getConfigFile().getBoolean("join.enabled");
        JOIN_FORMAT = BungeeStaffChat.getInstance().getConfigFile().getString("join.join-format");
        SWITCH_FORMAT = BungeeStaffChat.getInstance().getConfigFile().getString("join.switch-format");
        QUIT_FORMAT = BungeeStaffChat.getInstance().getConfigFile().getString("join.quit-format");

        PREFIX = BungeeStaffChat.getInstance().getConfigFile().getString("messages.prefix");
        NO_PERMISSION = BungeeStaffChat.getInstance().getConfigFile().getString("messages.no-permission");
        RELOAD = BungeeStaffChat.getInstance().getConfigFile().getString("messages.reload");
        STAFFCHAT_TOGGLE_ON = BungeeStaffChat.getInstance().getConfigFile().getString("messages.toggle-on");
        STAFFCHAT_TOGGLE_OFF = BungeeStaffChat.getInstance().getConfigFile().getString("messages.toggle-off");
        ADMINCHAT_TOGGLE_ON = BungeeStaffChat.getInstance().getConfigFile().getString("messages.adminchat-toggle-on");
        ADMINCHAT_TOGGLE_OFF = BungeeStaffChat.getInstance().getConfigFile().getString("messages.adminchat-toggle-off");
        DEVCHAT_TOGGLE_ON = BungeeStaffChat.getInstance().getConfigFile().getString("messages.devchat-toggle-on");
        DEVCHAT_TOGGLE_OFF = BungeeStaffChat.getInstance().getConfigFile().getString("messages.devchat-toggle-off");
        STAFFCHAT_OUTPUT = BungeeStaffChat.getInstance().getConfigFile().getString("messages.staffchat-output");
        STAFFCHAT_MESSAGE = BungeeStaffChat.getInstance().getConfigFile().getStringList("messages.staffchat-message");
    }
}
