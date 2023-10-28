package me.refracdevelopment.simplestaffchat.bungee.api;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;

/**
 * The BungeeStaffChatAPI allows you to hook into SimpleStaffChat to either modify and grab data
 * or to add new features and events.
 */
public class BungeeStaffChatAPI {

    public BungeeStaffChatAPI() {
        Color.log("&eSimpleStaffChatAPI has been enabled!");
        Color.log("&eWiki: https://refracdevelopment.gitbook.io/");
    }

    /**
     * @return Is the SimpleGemsAPI enabled and registered?
     */
    public boolean isRegistered() {
        return BungeeStaffChat.getInstance() != null;
    }

    /**
     * Allows you to register your own commands.
     * @return the command manager
     */
    public CommandManager getCommandManager() {
        return BungeeStaffChat.getInstance().getCommandManager();
    }

}