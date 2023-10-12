package me.refracdevelopment.simplestaffchat.spigot.api;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;

public class SimpleStaffChatAPI {

    private SimpleStaffChat plugin = SimpleStaffChat.getInstance();
    public static SimpleStaffChatAPI INSTANCE;

    public SimpleStaffChatAPI() {
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