package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.google.common.base.Joiner;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

public final class StaffChatCommand implements SimpleCommand {

    private VelocityStaffChat plugin;

    public StaffChatCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();

        String message = Joiner.on(" ").join(invocation.arguments());

        if (!commandSource.hasPermission(plugin.getCommands().STAFFCHAT_COMMAND_PERMISSION)) {
            Color.sendMessage(commandSource, plugin.getConfig().NO_PERMISSION);
            return;
        }

        if (invocation.arguments().length >= 1) {
            String format = (commandSource instanceof Player) ? plugin.getConfig().STAFFCHAT_FORMAT
                    .replace("%server%", ((Player) commandSource).getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", ((Player) commandSource).getUsername())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_STAFFCHAT_FORMAT
                    .replace("%server%", "N/A")
                    .replace("%player%", "Console")
                    .replace("%message%", message);

            Methods.sendStaffChat(commandSource, format, message);
        } else {
            if (plugin.getConfig().STAFFCHAT_OUTPUT.equalsIgnoreCase("default") && plugin.getConfig().STAFFCHAT_MESSAGE != null) {
                if (!commandSource.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(commandSource, plugin.getConfig().NO_PERMISSION);
                    return;
                }

                plugin.getConfig().STAFFCHAT_MESSAGE.forEach(s -> {
                    Color.sendMessage(commandSource, s);
                });
            } else if (plugin.getConfig().STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (commandSource instanceof Player) {
                    Player player = (Player) commandSource;

                    Methods.toggleStaffChat(player);
                }
            }
        }
    }
}