package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

public class ToggleCommand implements SimpleCommand {

    private VelocityStaffChat plugin;
    
    public ToggleCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();

        if (!(commandSource instanceof Player)) return;

        Player player = (Player) commandSource;

        if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
            Color.sendMessage(player, plugin.getConfig().NO_PERMISSION);
            return;
        }

        Methods.toggleStaffChat(player);
    }
}
