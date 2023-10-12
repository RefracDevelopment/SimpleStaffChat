package me.refracdevelopment.simplestaffchat.bungee.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    public StaffChatCommand() {
        super(Commands.STAFFCHAT_COMMAND, "", Commands.STAFFCHAT_ALIAS);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!Commands.STAFFCHAT_COMMAND_ENABLED) return;

        String message = Joiner.on(" ").join(strings);

        if (strings.length >= 1) {
            String format = (commandSender instanceof ProxiedPlayer) ? Config.STAFFCHAT_FORMAT.replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_STAFFCHAT_FORMAT.replace("%message%", message);

            Methods.sendStaffChat(commandSender, format);
        } else {
            if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("custom") && Config.STAFFCHAT_MESSAGE != null) {
                if (!commandSender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(commandSender, Config.NO_PERMISSION);
                    return;
                }

                Config.STAFFCHAT_MESSAGE.forEach(s -> {
                    Color.sendMessage(commandSender, s);
                });
            } else if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (commandSender instanceof ProxiedPlayer) {
                    ProxiedPlayer player = (ProxiedPlayer) commandSender;

                    Methods.toggleStaffChat(player);
                }
            } else {
                if (!commandSender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(commandSender, Config.NO_PERMISSION);
                    return;
                }

                Color.sendMessage(commandSender, "");
                Color.sendMessage(commandSender, "&c&lSimpleStaffChat2 %arrow% Help");
                Color.sendMessage(commandSender, "");
                Color.sendMessage(commandSender, "&c/" + Commands.STAFFCHAT_COMMAND + " <message> - Send staffchat messages.");
                Color.sendMessage(commandSender, "&c/" + Commands.TOGGLE_COMMAND + " - Send staffchat messages without needing to type a command.");
                Color.sendMessage(commandSender, "&c/" + Commands.ADMINCHAT_COMMAND + " <message> - Send adminchat messages.");
                Color.sendMessage(commandSender, "&c/" + Commands.ADMIN_TOGGLE_COMMAND + " - Send adminchat messages without needing to type a command.");
                Color.sendMessage(commandSender, "&c/" + Commands.DEVCHAT_COMMAND + " <message> - Send devchat messages.");
                Color.sendMessage(commandSender, "&c/" + Commands.DEV_TOGGLE_COMMAND + " - Send devchat messages without needing to type a command.");
                Color.sendMessage(commandSender, "&c/" + Commands.CHAT_COMMAND + " <type:staff|admin|dev> - Send chat messages without needing to type a command.");
                Color.sendMessage(commandSender, "&c/" + Commands.RELOAD_COMMAND + " - Reload the config file.");
                Color.sendMessage(commandSender, "");
            }
        }
    }
}