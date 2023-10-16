package me.refracdevelopment.simplestaffchat.bungee.commands.devchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DevChatCommand extends Command {

    private final BungeeStaffChat plugin;
    
    public DevChatCommand(BungeeStaffChat plugin) {
        super(plugin.getCommands().DEVCHAT_COMMAND, "", plugin.getCommands().DEVCHAT_COMMAND_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!plugin.getCommands().DEVCHAT_COMMAND_ENABLED) return;

        String message = Joiner.on(" ").join(strings);

        if (!commandSender.hasPermission(plugin.getCommands().DEVCHAT_COMMAND_PERMISSION)) {
            plugin.getColor().sendMessage(commandSender, plugin.getConfig().NO_PERMISSION);
            return;
        }

        if (strings.length >= 1) {
            String format = (commandSender instanceof ProxiedPlayer) ? plugin.getConfig().DEVCHAT_FORMAT.replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_DEVCHAT_FORMAT.replace("%message%", message);

            plugin.getMethods().sendDevChat(commandSender, format);
        }
    }
}