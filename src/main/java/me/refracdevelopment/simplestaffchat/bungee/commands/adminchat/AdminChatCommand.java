package me.refracdevelopment.simplestaffchat.bungee.commands.adminchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AdminChatCommand extends Command {
    
    private final BungeeStaffChat plugin;
    
    public AdminChatCommand(BungeeStaffChat plugin) {
        super(plugin.getCommands().ADMINCHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().ADMINCHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!commandSender.hasPermission(plugin.getCommands().ADMINCHAT_COMMAND_PERMISSION)) {
            Color.sendMessage(commandSender, "no-permission");
            return;
        }

        if (!(args.length >= 1)) {
            Color.sendMessage(commandSender, "usage", getName());
            return;
        }

        String format = (commandSender instanceof ProxiedPlayer) ? plugin.getConfig().ADMINCHAT_FORMAT
                .replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                .replace("%player%", commandSender.getName())
                .replace("%message%", message) : plugin.getConfig().CONSOLE_ADMINCHAT_FORMAT
                .replace("%server%", "N/A")
                .replace("%player%", commandSender.getName())
                .replace("%message%", message);

        Methods.sendAdminChat(commandSender, format, message);
    }
}