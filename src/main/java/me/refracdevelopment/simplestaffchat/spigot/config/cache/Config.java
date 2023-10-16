package me.refracdevelopment.simplestaffchat.spigot.config.cache;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.ConfigurationManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;

import java.util.List;

public class Config extends Manager {

    // General
    public String STAFFCHAT_SYMBOL;
    public String ADMINCHAT_SYMBOL;
    public String DEVCHAT_SYMBOL;

    // Format
    public String MINECRAFT_FORMAT;
    public String CONSOLE_FORMAT;
    public String ADMINCHAT_FORMAT;
    public String CONSOLE_ADMINCHAT_FORMAT;
    public String DEVCHAT_FORMAT;
    public String CONSOLE_DEVCHAT_FORMAT;

    // Join
    public boolean JOIN_ENABLED;
    public String JOIN_FORMAT;
    public String JOIN_QUIT_FORMAT;

    // Messages
    public String STAFFCHAT_OUTPUT;
    public List<String> STAFFCHAT_MESSAGE;

    // Settings
    public boolean BUNGEECORD;
    public String SERVER_NAME;
    public boolean SYMBOLS;

    public Config(SimpleStaffChat plugin) {
        super(plugin);
    }

    public void loadConfig() {
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

        plugin.getColor().log("&c==========================================");
        plugin.getColor().log("&aAll files have been loaded correctly!");
        plugin.getColor().log("&c==========================================");
    }
}