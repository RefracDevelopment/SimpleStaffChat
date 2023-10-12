package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.google.common.base.Joiner;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;

public final class StaffChatCommand implements SimpleCommand {
    
    @Override
    public void execute(Invocation invocation) {
        if (!Commands.STAFFCHAT_COMMAND_ENABLED.getBoolean()) return;
        CommandSource commandSource = invocation.source();

        String message = Joiner.on(" ").join(invocation.arguments());

        if (invocation.arguments().length >= 1) {
            String format = (commandSource instanceof Player) ? Config.STAFFCHAT_FORMAT.getString().replace("%server%", ((Player) commandSource).getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_STAFFCHAT_FORMAT.getString().replace("%message%", message);

            Methods.sendStaffChat(commandSource, format, message);
        } else {
            if (Config.STAFFCHAT_OUTPUT.getString().equalsIgnoreCase("default") && Config.STAFFCHAT_MESSAGE.getStringList() != null) {
                if (!commandSource.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(commandSource, Config.NO_PERMISSION.getString());
                    return;
                }

                Config.STAFFCHAT_MESSAGE.getStringList().forEach(s -> {
                    Color.sendMessage(commandSource, s);
                });
            } else if (Config.STAFFCHAT_OUTPUT.getString().equalsIgnoreCase("toggle")) {
                if (commandSource instanceof Player) {
                    Player player = (Player) commandSource;

                    Methods.toggleStaffChat(player);
                }
            }
        }
    }
}