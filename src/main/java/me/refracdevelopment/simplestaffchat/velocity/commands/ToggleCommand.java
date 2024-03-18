package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

public class ToggleCommand implements SimpleCommand {

    private final VelocityStaffChat plugin;
    
    public ToggleCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player)) {
            Color.sendMessage(invocation.source(), "no-console");
            return;
        }

        Player player = (Player) invocation.source();

        if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
            Color.sendMessage(player, plugin.getConfig().NO_PERMISSION);
            return;
        }

        Methods.toggleStaffChat(player);
    }
}
