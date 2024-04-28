package me.refracdevelopment.simplestaffchat.commands.devchat;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;

public class DevToggleCommand implements SimpleCommand {

    private final SimpleStaffChat plugin;

    public DevToggleCommand(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player player)) {
            Color.sendConfigMessage(invocation.source(), "no-console");
            return;
        }

        if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
            Color.sendConfigMessage(player, "no-permission");
            return;
        }

        Methods.toggleDevChat(player);
    }
}