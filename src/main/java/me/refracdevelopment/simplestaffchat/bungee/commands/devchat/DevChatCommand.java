package me.refracdevelopment.simplestaffchat.bungee.commands.devchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DevChatCommand extends Command {

    public DevChatCommand() {
        super(Commands.DEVCHAT_COMMAND, "", Commands.DEVCHAT_ALIAS);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!Commands.DEVCHAT_COMMAND_ENABLED) return;

        String message = Joiner.on(" ").join(strings);

        if (strings.length >= 1) {
            String format = (commandSender instanceof ProxiedPlayer) ? Config.DEVCHAT_FORMAT.replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_DEVCHAT_FORMAT.replace("%message%", message);

            Methods.sendDevChat(commandSender, format);
        }
    }
}