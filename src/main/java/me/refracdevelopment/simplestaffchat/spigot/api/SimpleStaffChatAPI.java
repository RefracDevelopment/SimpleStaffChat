package me.refracdevelopment.simplestaffchat.spigot.api;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;

public class SimpleStaffChatAPI extends Manager {

    public SimpleStaffChatAPI(SimpleStaffChat plugin) {
        super(plugin);
        plugin.getColor().log("&eSimpleStaffChatAPI has been enabled!");
        plugin.getColor().log("&eWiki: https://refracdevelopment.gitbook.io/");
}

    /**
     * @return Is the SimpleGemsAPI enabled and registered?
     */
    public boolean isRegistered() {
        return plugin != null;
    }

    /**
    * Allows you to register your own commands.
    * @return the command manager
    */
    public CommandManager getCommandManager() {
        return plugin.getCommandManager();
    }

}