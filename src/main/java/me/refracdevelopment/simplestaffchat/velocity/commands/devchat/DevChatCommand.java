package me.refracdevelopment.simplestaffchat.velocity.commands.devchat;

import com.google.common.base.Joiner;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;

public class DevChatCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        if (!Commands.DEVCHAT_COMMAND_ENABLED.getBoolean()) return;

        CommandSource commandSource = invocation.source();

        String message = Joiner.on(" ").join(invocation.arguments());

        if (invocation.arguments().length >= 1) {
            String format = (commandSource instanceof Player) ? Config.DEVCHAT_FORMAT.getString().replace("%server%", ((Player) commandSource).getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_DEVCHAT_FORMAT.getString().replace("%message%", message);

            Methods.sendDevChat(commandSource, format, message);
        }
    }
}