package me.refracdevelopment.simplestaffchat.bungee.commands.devchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DevChatCommand extends Command {

    private final BungeeStaffChat plugin;
    
    public DevChatCommand(BungeeStaffChat plugin) {
        super(plugin.getCommands().DEVCHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().DEVCHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!commandSender.hasPermission(plugin.getCommands().DEVCHAT_COMMAND_PERMISSION)) {
            Color.sendMessage(commandSender, "no-permission");
            return;
        }

        if (!(args.length >= 1)) {
            Color.sendMessage(commandSender, "usage", getName());
            return;
        }

        String format = (commandSender instanceof ProxiedPlayer) ? plugin.getConfig().DEVCHAT_FORMAT
                .replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                .replace("%player%", commandSender.getName())
                .replace("%message%", message) : plugin.getConfig().CONSOLE_DEVCHAT_FORMAT
                .replace("%server%", "N/A")
                .replace("%player%", commandSender.getName())
                .replace("%message%", message);

        Methods.sendDevChat(commandSender, format, message);
    }
}