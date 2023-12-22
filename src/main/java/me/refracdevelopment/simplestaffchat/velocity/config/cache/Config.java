package me.refracdevelopment.simplestaffchat.velocity.config.cache;

import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

import java.util.List;

public class Config {

    // General
    public boolean SYMBOLS;
    public String STAFFCHAT_SYMBOL;
    public String ADMINCHAT_SYMBOL;
    public String DEVCHAT_SYMBOL;
    public String STAFFCHAT_FORMAT;
    public String CONSOLE_STAFFCHAT_FORMAT;
    public String ADMINCHAT_FORMAT;
    public String CONSOLE_ADMINCHAT_FORMAT;
    public String DEVCHAT_FORMAT;
    public String CONSOLE_DEVCHAT_FORMAT;
    public boolean JOIN_ENABLED;
    public String JOIN_FORMAT;
    public String SWITCH_FORMAT;
    public String QUIT_FORMAT;
    public String PREFIX;
    public String NO_PERMISSION;
    public String RELOAD;
    public String STAFFCHAT_TOGGLE_ON;
    public String STAFFCHAT_TOGGLE_OFF;
    public String ADMINCHAT_TOGGLE_ON;
    public String ADMINCHAT_TOGGLE_OFF;
    public String DEVCHAT_TOGGLE_ON;
    public String DEVCHAT_TOGGLE_OFF;
    public String ALLCHAT_TOGGLE_ON;
    public String STAFFCHAT_MUTED_ON;
    public String STAFFCHAT_MUTED_OFF;
    public String ADMINCHAT_MUTED_ON;
    public String ADMINCHAT_MUTED_OFF;
    public String DEVCHAT_MUTED_ON;
    public String DEVCHAT_MUTED_OFF;
    public String ALLCHAT_MUTED_ON;
    public String ALLCHAT_MUTED_OFF;
    public String STAFFCHAT_OUTPUT;
    public List<String> STAFFCHAT_MESSAGE;

    public Config() {
        loadConfig();
    }

    public void loadConfig() {
        SYMBOLS = VelocityStaffChat.getInstance().getConfigFile().getBoolean("symbols.enabled");
        STAFFCHAT_SYMBOL = VelocityStaffChat.getInstance().getConfigFile().getString("symbols.staffchat-symbol");
        ADMINCHAT_SYMBOL = VelocityStaffChat.getInstance().getConfigFile().getString("symbols.adminchat-symbol");
        DEVCHAT_SYMBOL = VelocityStaffChat.getInstance().getConfigFile().getString("symbols.devchat-symbol");
        STAFFCHAT_FORMAT = VelocityStaffChat.getInstance().getConfigFile().getString("format.minecraft-format");
        CONSOLE_STAFFCHAT_FORMAT = VelocityStaffChat.getInstance().getConfigFile().getString("format.console-staffchat-format");
        ADMINCHAT_FORMAT = VelocityStaffChat.getInstance().getConfigFile().getString("format.adminchat-format");
        CONSOLE_ADMINCHAT_FORMAT = VelocityStaffChat.getInstance().getConfigFile().getString("format.console-adminchat-format");
        DEVCHAT_FORMAT = VelocityStaffChat.getInstance().getConfigFile().getString("format.devchat-format");
        CONSOLE_DEVCHAT_FORMAT = VelocityStaffChat.getInstance().getConfigFile().getString("format.console-devchat-format");
        JOIN_ENABLED = VelocityStaffChat.getInstance().getConfigFile().getBoolean("join.enabled");
        JOIN_FORMAT = VelocityStaffChat.getInstance().getConfigFile().getString("join.join-format");
        SWITCH_FORMAT = VelocityStaffChat.getInstance().getConfigFile().getString("join.switch-format");
        QUIT_FORMAT = VelocityStaffChat.getInstance().getConfigFile().getString("join.quit-format");
        PREFIX = VelocityStaffChat.getInstance().getConfigFile().getString("messages.prefix");
        NO_PERMISSION = VelocityStaffChat.getInstance().getConfigFile().getString("messages.no-permission");
        RELOAD = VelocityStaffChat.getInstance().getConfigFile().getString("messages.reload");
        STAFFCHAT_TOGGLE_ON = VelocityStaffChat.getInstance().getConfigFile().getString("messages.staffchat-toggle-on");
        STAFFCHAT_TOGGLE_OFF = VelocityStaffChat.getInstance().getConfigFile().getString("messages.staffchat-toggle-off");
        ADMINCHAT_TOGGLE_ON = VelocityStaffChat.getInstance().getConfigFile().getString("messages.adminchat-toggle-on");
        ADMINCHAT_TOGGLE_OFF = VelocityStaffChat.getInstance().getConfigFile().getString("messages.adminchat-toggle-off");
        DEVCHAT_TOGGLE_ON = VelocityStaffChat.getInstance().getConfigFile().getString("messages.devchat-toggle-on");
        DEVCHAT_TOGGLE_OFF = VelocityStaffChat.getInstance().getConfigFile().getString("messages.devchat-toggle-off");
        ALLCHAT_TOGGLE_ON = VelocityStaffChat.getInstance().getConfigFile().getString("messages.allcat-toggle-on");
        STAFFCHAT_MUTED_ON = VelocityStaffChat.getInstance().getConfigFile().getString("messages.staffchat-muted-on");
        STAFFCHAT_MUTED_OFF = VelocityStaffChat.getInstance().getConfigFile().getString("messages.staffchat-muted-off");
        ADMINCHAT_MUTED_ON = VelocityStaffChat.getInstance().getConfigFile().getString("messages.adminchat-muted-on");
        ADMINCHAT_MUTED_OFF = VelocityStaffChat.getInstance().getConfigFile().getString("messages.adminchat-muted-off");
        DEVCHAT_MUTED_ON = VelocityStaffChat.getInstance().getConfigFile().getString("messages.devchat-muted-on");
        DEVCHAT_MUTED_OFF = VelocityStaffChat.getInstance().getConfigFile().getString("messages.devchat-muted-off");
        ALLCHAT_MUTED_ON = VelocityStaffChat.getInstance().getConfigFile().getString("messages.allchat-muted-on");
        ALLCHAT_MUTED_OFF = VelocityStaffChat.getInstance().getConfigFile().getString("messages.allchat-muted-off");
        STAFFCHAT_OUTPUT = VelocityStaffChat.getInstance().getConfigFile().getString("messages.staffchat-output");
        STAFFCHAT_MESSAGE = VelocityStaffChat.getInstance().getConfigFile().getStringList("messages.staffchat-message");
    }
}