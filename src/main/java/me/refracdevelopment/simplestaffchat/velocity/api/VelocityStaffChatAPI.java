package me.refracdevelopment.simplestaffchat.velocity.api;

import com.velocitypowered.api.command.CommandManager;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Manager;

/**
 * The VelocityStaffChatAPI allows you to hook into SimpleStaffChat to either modify and grab data
 * or to add new features and events.
 */
public class VelocityStaffChatAPI extends Manager {

    public VelocityStaffChatAPI(VelocityStaffChat plugin) {
        super(plugin);
        plugin.getColor().log("<yellow>SimpleStaffChatAPI has been enabled!");
        plugin.getColor().log("<yellow>Wiki: https://refracdevelopment.gitbook.io/");
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
        return plugin.getServer().getCommandManager();
    }

}