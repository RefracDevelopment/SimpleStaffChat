package me.refracdevelopment.simplestaffchat.manager.config.cache;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;


public class Config {
    // General
    public String SERVER_NAME;

    // Symbols
    public String STAFFCHAT_SYMBOL;
    public String ADMINCHAT_SYMBOL;
    public String DEVCHAT_SYMBOL;

    // Format
    public String STAFFCHAT_FORMAT;
    public String CONSOLE_STAFFCHAT_FORMAT;
    public String ADMINCHAT_FORMAT;
    public String CONSOLE_ADMINCHAT_FORMAT;
    public String DEVCHAT_FORMAT;
    public String CONSOLE_DEVCHAT_FORMAT;

    // Join
    public String JOIN_FORMAT;
    public String QUIT_FORMAT;

    // Modules
    public boolean STAFFCHAT_ENABLED;
    public boolean ADMINCHAT_ENABLED;
    public boolean DEVCHAT_ENABLED;
    public boolean DISCORD_ENABLED;
    public boolean JOIN_ENABLED;
    public boolean CHAT_TOGGLE_ENABLED;
    public boolean SYMBOLS_ENABLED;

    public Config() {
        loadConfig();
    }


    public void loadConfig() {
        this.SERVER_NAME = SimpleStaffChat.getInstance().getConfigFile().getString("server-name");

        this.STAFFCHAT_SYMBOL = SimpleStaffChat.getInstance().getLocaleFile().getString("staffchat-symbol");
        this.ADMINCHAT_SYMBOL = SimpleStaffChat.getInstance().getLocaleFile().getString("adminchat-symbol");
        this.DEVCHAT_SYMBOL = SimpleStaffChat.getInstance().getLocaleFile().getString("devchat-symbol");

        this.STAFFCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("staffchat-format");
        this.CONSOLE_STAFFCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("console-staffchat-format");
        this.ADMINCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("adminchat-format");
        this.CONSOLE_ADMINCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("fconsole-adminchat-format");
        this.DEVCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("devchat-format");
        this.CONSOLE_DEVCHAT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("console-devchat-format");

        this.JOIN_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("join-format");
        this.QUIT_FORMAT = SimpleStaffChat.getInstance().getLocaleFile().getString("quit-format");

        this.STAFFCHAT_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.staffchat");
        this.ADMINCHAT_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.adminchat");
        this.DEVCHAT_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.devchat");
        this.DISCORD_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.discord");
        this.JOIN_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.join");
        this.CHAT_TOGGLE_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.chat-toggle");
        this.SYMBOLS_ENABLED = SimpleStaffChat.getInstance().getConfigFile().getBoolean("modules.symbols");
    }
}