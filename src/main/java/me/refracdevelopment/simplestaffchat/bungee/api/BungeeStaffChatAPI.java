package me.refracdevelopment.simplestaffchat.bungee.api;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;

/**
 * The BungeeStaffChatAPI allows you to hook into SimpleStaffChat to either modify and grab data
 * or to add new features and events.
 */
public class BungeeStaffChatAPI {

    private BungeeStaffChat plugin = BungeeStaffChat.getInstance();
    public static BungeeStaffChatAPI INSTANCE;

    public BungeeStaffChatAPI() {
        INSTANCE = this;
        Color.log("&eSimpleStaffChatAPI has been enabled!");
        Color.log("&eWiki: https://refracdevelopment.gitbook.io/");
    }

    /**
     * @return Is the SimpleGemsAPI enabled and registered?
     */
    public static boolean isRegistered() {
        return INSTANCE != null;
    }

    /**
     * Allows you to register your own commands.
     * @return the command manager
     */
    public CommandManager getCommandManager() {
        return plugin.getCommandManager();
    }

}