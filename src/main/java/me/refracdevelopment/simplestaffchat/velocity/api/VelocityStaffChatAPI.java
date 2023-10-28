package me.refracdevelopment.simplestaffchat.velocity.api;

import com.velocitypowered.api.command.CommandManager;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

/**
 * The VelocityStaffChatAPI allows you to hook into SimpleStaffChat to either modify and grab data
 * or to add new features and events.
 */
public class VelocityStaffChatAPI {

    public VelocityStaffChatAPI() {
        Color.log("<green>SimpleStaffChatAPI has been enabled!");
        Color.log("<green>Wiki: https://refracdevelopment.gitbook.io/");
    }

    /**
     * @return Is the SimpleGemsAPI enabled and registered?
     */
    public boolean isRegistered() {
        return VelocityStaffChat.getInstance() != null;
    }

    /**
     * Allows you to register your own commands.
     * @return the command manager
     */
    public CommandManager getCommandManager() {
        return VelocityStaffChat.getInstance().getServer().getCommandManager();
    }

}