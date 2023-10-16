package me.refracdevelopment.simplestaffchat.velocity.commands.adminchat;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

public class AdminHideCommand implements SimpleCommand {

    private VelocityStaffChat plugin;

    public AdminHideCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!plugin.getCommands().ADMIN_HIDE_COMMAND_ENABLED) return;
        if (!(invocation.source() instanceof Player)) return;

        Player player = (Player) invocation.source();

        if (!player.hasPermission(plugin.getCommands().ADMIN_HIDE_COMMAND_PERMISSION)) {
            plugin.getColor().sendMessage(player, plugin.getConfig().NO_PERMISSION);
            return;
        }

        plugin.getMethods().hideAdminChat(player);
    }
}