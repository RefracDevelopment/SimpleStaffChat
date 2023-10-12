package me.refracdevelopment.simplestaffchat.velocity.api;

import com.velocitypowered.api.command.CommandManager;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;

/**
 * The VelocityStaffChatAPI allows you to hook into SimpleStaffChat to either modify and grab data
 * or to add new features and events.
 */
public class VelocityStaffChatAPI {

    private VelocityStaffChat plugin = VelocityStaffChat.getInstance();
    public static VelocityStaffChatAPI INSTANCE;

    public VelocityStaffChatAPI() {
        INSTANCE = this;
        Color.log("<yellow>SimpleStaffChatAPI has been enabled!");
        Color.log("<yellow>Wiki: https://refracdevelopment.gitbook.io/");
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
        return plugin.getServer().getCommandManager();
    }

}