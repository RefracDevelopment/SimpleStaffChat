package me.refracdevelopment.simplestaffchat.spigot.config.cache;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;

import java.util.List;

public class Config extends Manager {

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
    public String STAFFCHAT_OUTPUT;
    public List<String> STAFFCHAT_MESSAGE;

    public Config(SimpleStaffChat plugin) {
        super(plugin);
        loadConfig();
    }

    public void loadConfig() {
        BUNGEECORD = plugin.getConfigFile().getBoolean("bungeecord");
        SERVER_NAME = plugin.getConfigFile().getString("server-name");
        SYMBOLS = plugin.getConfigFile().getBoolean("symbols");

        STAFFCHAT_SYMBOL = plugin.getConfigFile().getString("staffchat-symbol");
        ADMINCHAT_SYMBOL = plugin.getConfigFile().getString("adminchat-symbol");
        DEVCHAT_SYMBOL = plugin.getConfigFile().getString("devchat-symbol");

        STAFFCHAT_FORMAT = plugin.getConfigFile().getString("format.minecraft-format");
        CONSOLE_STAFFCHAT_FORMAT = plugin.getConfigFile().getString("format.console-staffchat-format");
        ADMINCHAT_FORMAT = plugin.getConfigFile().getString("format.adminchat-format");
        CONSOLE_ADMINCHAT_FORMAT = plugin.getConfigFile().getString("format.console-adminchat-format");
        DEVCHAT_FORMAT = plugin.getConfigFile().getString("format.devchat-format");
        CONSOLE_DEVCHAT_FORMAT = plugin.getConfigFile().getString("format.console-devchat-format");

        JOIN_ENABLED = plugin.getConfigFile().getBoolean("join.enabled");
        JOIN_FORMAT = plugin.getConfigFile().getString("join.join-format");
        SWITCH_FORMAT = plugin.getConfigFile().getString("join.switch-format");
        QUIT_FORMAT = plugin.getConfigFile().getString("join.quit-format");

        PREFIX = plugin.getConfigFile().getString("messages.prefix");
        NO_PERMISSION = plugin.getConfigFile().getString("messages.no-permission");
        RELOAD = plugin.getConfigFile().getString("messages.reload");
        STAFFCHAT_TOGGLE_ON = plugin.getConfigFile().getString("messages.toggle-on");
        STAFFCHAT_TOGGLE_OFF = plugin.getConfigFile().getString("messages.toggle-off");
        ADMINCHAT_TOGGLE_ON = plugin.getConfigFile().getString("messages.adminchat-toggle-on");
        ADMINCHAT_TOGGLE_OFF = plugin.getConfigFile().getString("messages.adminchat-toggle-off");
        DEVCHAT_TOGGLE_ON = plugin.getConfigFile().getString("messages.devchat-toggle-on");
        DEVCHAT_TOGGLE_OFF = plugin.getConfigFile().getString("messages.devchat-toggle-off");
        ALLCHAT_TOGGLE_ON = plugin.getConfigFile().getString("messages.allchat-toggle-on");
        STAFFCHAT_MUTED_ON = plugin.getConfigFile().getString("messages.staffchat-muted-on");
        STAFFCHAT_MUTED_OFF = plugin.getConfigFile().getString("messages.staffchat-muted-off");
        ADMINCHAT_MUTED_ON = plugin.getConfigFile().getString("messages.adminchat-muted-on");
        ADMINCHAT_MUTED_OFF = plugin.getConfigFile().getString("messages.adminchat-muted-off");
        DEVCHAT_MUTED_ON = plugin.getConfigFile().getString("messages.devchat-muted-on");
        DEVCHAT_MUTED_OFF = plugin.getConfigFile().getString("messages.devchat-muted-off");
        STAFFCHAT_OUTPUT = plugin.getConfigFile().getString("messages.staffchat-output");
        STAFFCHAT_MESSAGE = plugin.getConfigFile().getStringList("messages.staffchat-message");
    }
}