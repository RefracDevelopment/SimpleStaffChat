package me.refracdevelopment.simplestaffchat.velocity.commands.devchat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

public class DevToggleCommand implements SimpleCommand {

    private final VelocityStaffChat plugin;

    public DevToggleCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();

        if (!(commandSource instanceof Player)) return;

        Player player = (Player) invocation.source();

        if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
            Color.sendMessage(player, plugin.getConfig().NO_PERMISSION);
            return;
        }

        Methods.toggleDevChat(player);
    }
}