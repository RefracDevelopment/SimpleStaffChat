package me.refracdevelopment.simplestaffchat.spigot.api;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;

public class SimpleStaffChatAPI {

    public SimpleStaffChatAPI() {
        Color.log("&aSimpleStaffChatAPI has been enabled!");
        Color.log("&aWiki: https://refracdevelopment.gitbook.io/");
}

    /**
     * @return Is the SimpleGemsAPI enabled and registered?
     */
    public boolean isRegistered() {
        return SimpleStaffChat.getInstance() != null;
    }

    /**
    * Allows you to register your own commands.
    * @return the command manager
    */
    public CommandManager getCommandManager() {
        return SimpleStaffChat.getInstance().getCommandManager();
    }

}