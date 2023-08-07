package me.refracdevelopment.simplestaffchat.bungee.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    private final BungeeStaffChat plugin;

    public StaffChatCommand(BungeeStaffChat plugin) {
        super(Commands.STAFFCHAT_COMMAND, "", Commands.STAFFCHAT_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!Commands.STAFFCHAT_COMMAND_ENABLED) return;

        if (strings.length >= 1) {
            String format;
            String message = Joiner.on(" ").join(strings);

            format = (commandSender instanceof ProxiedPlayer) ? Config.STAFFCHAT_FORMAT.replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_STAFFCHAT_FORMAT.replace("%message%", message);

            if (!commandSender.hasPermission(Permissions.STAFFCHAT_COMMAND)) {
                Color.sendMessage(commandSender, Config.NO_PERMISSION);
                return;
            }

            for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(commandSender, format));
                }
            }
            Color.log2(Color.translate(commandSender, format));
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

                    if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                        Color.sendMessage(player, Config.NO_PERMISSION);
                        return;
                    }

                    if (ToggleCommand.insc.contains(player.getUniqueId())) {
                        ToggleCommand.insc.remove(player.getUniqueId());
                        Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_OFF);
                    } else {
                        ToggleCommand.insc.add(player.getUniqueId());
                        Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_ON);
                    }
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
                Color.sendMessage(commandSender, "&c/" + Commands.RELOAD_COMMAND + " - Reload the config file.");
                Color.sendMessage(commandSender, "");
            }
        }
    }
}