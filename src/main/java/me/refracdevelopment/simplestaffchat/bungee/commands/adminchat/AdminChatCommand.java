package me.refracdevelopment.simplestaffchat.bungee.commands.adminchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AdminChatCommand extends Command {

    private final BungeeStaffChat plugin;

    public AdminChatCommand(BungeeStaffChat plugin) {
        super(Commands.ADMINCHAT_COMMAND, "", Commands.ADMINCHAT_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!Commands.ADMINCHAT_COMMAND_ENABLED) return;

        if (strings.length >= 1) {
            String format;
            String message = Joiner.on(" ").join(strings);

            format = (commandSender instanceof ProxiedPlayer) ? Config.ADMINCHAT_FORMAT.replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_ADMINCHAT_FORMAT.replace("%message%", message);

            if (!commandSender.hasPermission(Permissions.ADMINCHAT_COMMAND)) {
                Color.sendMessage(commandSender, Config.NO_PERMISSION);
                return;
            }

            for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(commandSender, format));
                }
            }
            Color.log2(Color.translate(commandSender, format));
        }
    }
}