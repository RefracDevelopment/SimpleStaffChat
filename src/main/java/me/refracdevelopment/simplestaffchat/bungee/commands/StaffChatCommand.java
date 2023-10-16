package me.refracdevelopment.simplestaffchat.bungee.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    private final BungeeStaffChat plugin;
    
    public StaffChatCommand(BungeeStaffChat plugin) {
        super(plugin.getCommands().STAFFCHAT_COMMAND, "", plugin.getCommands().STAFFCHAT_COMMAND_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!plugin.getCommands().STAFFCHAT_COMMAND_ENABLED) return;

        String message = Joiner.on(" ").join(strings);

        if (!commandSender.hasPermission(plugin.getCommands().STAFFCHAT_COMMAND_PERMISSION)) {
            plugin.getColor().sendMessage(commandSender, plugin.getConfig().NO_PERMISSION);
            return;
        }

        if (strings.length >= 1) {
            String format = (commandSender instanceof ProxiedPlayer) ? plugin.getConfig().STAFFCHAT_FORMAT.replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_STAFFCHAT_FORMAT.replace("%message%", message);

            plugin.getMethods().sendStaffChat(commandSender, format);
        } else {
            if (plugin.getConfig().STAFFCHAT_OUTPUT.equalsIgnoreCase("custom") && plugin.getConfig().STAFFCHAT_MESSAGE != null) {
                if (!commandSender.hasPermission(plugin.getPermissions().STAFFCHAT_HELP)) {
                    plugin.getColor().sendMessage(commandSender, plugin.getConfig().NO_PERMISSION);
                    return;
                }

                plugin.getConfig().STAFFCHAT_MESSAGE.forEach(s -> {
                    plugin.getColor().sendMessage(commandSender, s);
                });
            } else if (plugin.getConfig().STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (commandSender instanceof ProxiedPlayer) {
                    ProxiedPlayer player = (ProxiedPlayer) commandSender;

                    plugin.getMethods().toggleStaffChat(player);
                }
            } else {
                if (!commandSender.hasPermission(plugin.getPermissions().STAFFCHAT_HELP)) {
                    plugin.getColor().sendMessage(commandSender, plugin.getConfig().NO_PERMISSION);
                    return;
                }

                plugin.getColor().sendMessage(commandSender, "");
                plugin.getColor().sendMessage(commandSender, "&c&lSimpleStaffChat2 %arrow% Help");
                plugin.getColor().sendMessage(commandSender, "");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().STAFFCHAT_COMMAND + " <message> - Send staffchat messages.");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().STAFF_TOGGLE_COMMAND + " - Send staffchat messages without needing to type a command.");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().ADMINCHAT_COMMAND + " <message> - Send adminchat messages.");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().ADMIN_TOGGLE_COMMAND + " - Send adminchat messages without needing to type a command.");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().DEVCHAT_COMMAND + " <message> - Send devchat messages.");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().DEV_TOGGLE_COMMAND + " - Send devchat messages without needing to type a command.");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().CHAT_COMMAND + " <type:staff|admin|dev> - Send chat messages without needing to type a command.");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().STAFF_HIDE_COMMAND + " <type:staff|admin|dev> - Allows you to hide staffchat messages.");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().ADMIN_HIDE_COMMAND + " <type:staff|admin|dev> - Allows you to hide adminchat messages.");
                plugin.getColor().sendMessage(commandSender, "&c/" + plugin.getCommands().DEV_HIDE_COMMAND + " <type:staff|admin|dev> - Allows you to hide devchat messages.");
                plugin.getColor().sendMessage(commandSender, "&c/staffchatreload - Reload the config file.");
                plugin.getColor().sendMessage(commandSender, "");
            }
        }
    }
}