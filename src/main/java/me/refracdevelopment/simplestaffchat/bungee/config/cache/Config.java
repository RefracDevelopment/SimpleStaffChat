package me.refracdevelopment.simplestaffchat.bungee.config.cache;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;

import java.util.List;

public class Config {

    // General
    public String STAFFCHAT_SYMBOL;
    public String ADMINCHAT_SYMBOL;
    public String DEVCHAT_SYMBOL;
    public boolean LUCKPERMS;
    public boolean SYMBOLS;

    // Format
    public String STAFFCHAT_FORMAT;
    public String CONSOLE_STAFFCHAT_FORMAT;
    public String ADMINCHAT_FORMAT;
    public String CONSOLE_ADMINCHAT_FORMAT;
    public String DEVCHAT_FORMAT;
    public String CONSOLE_DEVCHAT_FORMAT;

    // Join
    public boolean JOIN_ENABLED;
    public String JOIN_FORMAT;
    public String SWITCH_FORMAT;
    public String QUIT_FORMAT;

    // Messages
    public String STAFFCHAT_OUTPUT;
    public List<String> STAFFCHAT_MESSAGE;

    public Config() {
        loadConfig();
    }

    public void loadConfig() {
        STAFFCHAT_SYMBOL = BungeeStaffChat.getInstance().getConfigFile().getString("staffchat-symbol");
        ADMINCHAT_SYMBOL = BungeeStaffChat.getInstance().getConfigFile().getString("adminchat-symbol");
        DEVCHAT_SYMBOL = BungeeStaffChat.getInstance().getConfigFile().getString("devchat-symbol");
        LUCKPERMS = BungeeStaffChat.getInstance().getConfigFile().getBoolean("luckperms");
        SYMBOLS = BungeeStaffChat.getInstance().getConfigFile().getBoolean("symbols");

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

        STAFFCHAT_OUTPUT = BungeeStaffChat.getInstance().getConfigFile().getString("staffchat-output");
        STAFFCHAT_MESSAGE = BungeeStaffChat.getInstance().getConfigFile().getStringList("staffchat-message");
    }
}
