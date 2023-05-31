package me.refracdevelopment.simplestaffchat.bungee.commands.devchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DevChatCommand extends Command {

    private final BungeeStaffChat plugin;

    public DevChatCommand(BungeeStaffChat plugin) {
        super(Commands.DEVCHAT_COMMAND, "", Commands.DEVCHAT_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!Commands.DEVCHAT_COMMAND_ENABLED) return;

        if (strings.length >= 1) {
            String format;
            String message = Joiner.on(" ").join(strings);

            format = (commandSender instanceof ProxiedPlayer) ? Config.DEVCHAT_FORMAT.replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_DEVCHAT_FORMAT.replace("%message%", message);

            if (!commandSender.hasPermission(Permissions.DEVCHAT_TOGGLE)) {
                Color.sendMessage(commandSender, Config.NO_PERMISSION, true);
                return;
            }

            for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
                if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(commandSender, format), true);
                }
            }
            Color.log2(Color.translate(commandSender, format));
        }
    }
}