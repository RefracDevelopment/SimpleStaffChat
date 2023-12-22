package me.refracdevelopment.simplestaffchat.bungee.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    private final BungeeStaffChat plugin;
    
    public StaffChatCommand(BungeeStaffChat plugin) {
        super(plugin.getCommands().STAFFCHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().STAFFCHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!commandSender.hasPermission(plugin.getCommands().STAFFCHAT_COMMAND_PERMISSION)) {
            Color.sendMessage(commandSender, "no-permission");
            return;
        }

        if (args.length >= 1) {
            String format = (commandSender instanceof ProxiedPlayer) ? plugin.getConfig().STAFFCHAT_FORMAT
                    .replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%player%", commandSender.getName())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_STAFFCHAT_FORMAT
                    .replace("%server%", "N/A")
                    .replace("%player%", commandSender.getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(commandSender, format);
        } else {
            if (plugin.getConfig().STAFFCHAT_OUTPUT.equalsIgnoreCase("default") ||
                    plugin.getConfig().STAFFCHAT_OUTPUT.equalsIgnoreCase("custom")) {
                if (!commandSender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(commandSender, "no-permission");
                    return;
                }

                plugin.getConfig().STAFFCHAT_MESSAGE.forEach(s -> {
                    Color.sendCustomMessage(commandSender, s);
                });
            } else if (plugin.getConfig().STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (commandSender instanceof ProxiedPlayer) {
                    ProxiedPlayer player = (ProxiedPlayer) commandSender;

                    Methods.toggleStaffChat(player);
                }
            }
        }
    }
}