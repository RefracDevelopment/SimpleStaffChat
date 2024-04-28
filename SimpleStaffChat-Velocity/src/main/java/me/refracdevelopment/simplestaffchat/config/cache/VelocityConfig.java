package me.refracdevelopment.simplestaffchat.config.cache;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;

public class VelocityConfig {

    // General
    public String SERVER_NAME;

    // Format
    public String STAFFCHAT_SYMBOL;
    public String ADMINCHAT_SYMBOL;
    public String DEVCHAT_SYMBOL;
    public String STAFFCHAT_FORMAT;
    public String CONSOLE_STAFFCHAT_FORMAT;
    public String ADMINCHAT_FORMAT;
    public String CONSOLE_ADMINCHAT_FORMAT;
    public String DEVCHAT_FORMAT;
    public String CONSOLE_DEVCHAT_FORMAT;

    // Join
    public String JOIN_FORMAT;
    public String SWITCH_FORMAT;
    public String QUIT_FORMAT;

    // Modules
    public boolean STAFFCHAT_ENABLED, ADMINCHAT_ENABLED, DEVCHAT_ENABLED, DISCORD_ENABLED, JOIN_ENABLED, CHAT_TOGGLE_ENABLED, SYMBOLS_ENABLED;

    public VelocityConfig() {
        loadConfig();
    }

    public void loadConfig() {
        // General
        SERVER_NAME = SimpleStaffChat.getInstance().getConfigFile().getString("server-name");

        // Format
        STAFFCHAT_SYMBOL = SimpleStaffChat.getInstance().getLocaleFile().getString("staffchat-symbol");
        ADMINCHAT_SYMBOL = SimpleStaffChat.getInstance().getLocaleFile().getString("adminchat-symbol");
        DEVCHAT_SYMBOL = SimpleStaffChat.getInstance().getLocaleFile().getString("devchat-symbol");
        STAFFCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("staffchat-format");
        CONSOLE_STAFFCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("console-staffchat-format");
        ADMINCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("adminchat-format");
        CONSOLE_ADMINCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("console-adminchat-format");
        DEVCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("devchat-format");
        CONSOLE_DEVCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("console-devchat-format");

        // Join
        JOIN_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("join-format");
        SWITCH_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("switch-format");
        QUIT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("quit-format");

        // Modules
        STAFFCHAT_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.staffchat");
        ADMINCHAT_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.adminchat");
        DEVCHAT_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.devchat");
        DISCORD_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.discord");
        JOIN_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.join");
        CHAT_TOGGLE_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.chat-toggle");
        SYMBOLS_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.symbols");
    }
}