package me.refracdevelopment.simplestaffchat.spigot.config.cache;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;

import java.util.List;

public class Config {

    // General
    public String STAFFCHAT_SYMBOL;
    public String ADMINCHAT_SYMBOL;
    public String DEVCHAT_SYMBOL;
    public boolean SYMBOLS;
    public boolean BUNGEECORD;
    public String SERVER_NAME;

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
        BUNGEECORD = SimpleStaffChat.getInstance().getConfigFile().getBoolean("bungeecord");
        SERVER_NAME = SimpleStaffChat.getInstance().getConfigFile().getString("server-name");

        SYMBOLS = SimpleStaffChat.getInstance().getConfigFile().getBoolean("symbols.enabled");
        STAFFCHAT_SYMBOL = SimpleStaffChat.getInstance().getConfigFile().getString("symbols.staffchat-symbol");
        ADMINCHAT_SYMBOL = SimpleStaffChat.getInstance().getConfigFile().getString("symbols.adminchat-symbol");
        DEVCHAT_SYMBOL = SimpleStaffChat.getInstance().getConfigFile().getString("symbols.devchat-symbol");

        STAFFCHAT_FORMAT = SimpleStaffChat.getInstance().getConfigFile().getString("format.minecraft-format");
        CONSOLE_STAFFCHAT_FORMAT = SimpleStaffChat.getInstance().getConfigFile().getString("format.console-staffchat-format");
        ADMINCHAT_FORMAT = SimpleStaffChat.getInstance().getConfigFile().getString("format.adminchat-format");
        CONSOLE_ADMINCHAT_FORMAT = SimpleStaffChat.getInstance().getConfigFile().getString("format.console-adminchat-format");
        DEVCHAT_FORMAT = SimpleStaffChat.getInstance().getConfigFile().getString("format.devchat-format");
        CONSOLE_DEVCHAT_FORMAT = SimpleStaffChat.getInstance().getConfigFile().getString("format.console-devchat-format");

        JOIN_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("join.enabled");
        JOIN_FORMAT = SimpleStaffChat.getInstance().getConfigFile().getString("join.join-format");
        SWITCH_FORMAT = SimpleStaffChat.getInstance().getConfigFile().getString("join.switch-format");
        QUIT_FORMAT = SimpleStaffChat.getInstance().getConfigFile().getString("join.quit-format");

        STAFFCHAT_OUTPUT = SimpleStaffChat.getInstance().getConfigFile().getString("staffchat-output");
        STAFFCHAT_MESSAGE = SimpleStaffChat.getInstance().getConfigFile().getStringList("staffchat-message");
    }
}