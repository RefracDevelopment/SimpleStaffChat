package me.refracdevelopment.simplestaffchat.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;

public class ToggleCommand implements SimpleCommand {

    private final SimpleStaffChat plugin;

    public ToggleCommand(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player player)) {
            RyMessageUtils.sendPluginMessage(invocation.source(), "no-console");
            return;
        }

        if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(player, "no-permission");
            return;
        }

        if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
            RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
            return;
        }

        Methods.toggleStaffChat(player);
    }
}
