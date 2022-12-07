package me.refracdevelopment.simplestaffchat.bungee.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.config.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@CommandAlias("staffchat|sc")
@CommandPermission(Permissions.STAFFCHAT_COMMAND)
@Description("Send messages to your staff privately")
public class StaffChatCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            String format;
            String message = Joiner.on(" ").join(args);

            format = (sender instanceof ProxiedPlayer) ? Config.FORMAT_STAFFCHAT.toString().replace("%server%", ((ProxiedPlayer) sender).getServer().getInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_STAFFCHAT.toString().replace("%message%", message);

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(sender, format));
                }
            }
            ProxyServer.getInstance().getConsole().sendMessage(Color.translate(sender, format));
        } else {
            if (Config.MESSAGES_STAFFCHAT_OUTPUT.toString().equalsIgnoreCase("custom") && Config.MESSAGES_STAFFCHAT_MESSAGE.toList() != null) {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(sender, "&c/staffchat <message>", true);
                    return;
                }

                for (String s : Config.MESSAGES_STAFFCHAT_MESSAGE.toList())
                    Color.sendMessage(sender, s, true);
            } else if (Config.MESSAGES_STAFFCHAT_OUTPUT.toString().equalsIgnoreCase("toggle")) {
                if (sender instanceof ProxiedPlayer) {
                    ProxiedPlayer player = (ProxiedPlayer) sender;

                    if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                        Color.sendMessage(player, Config.MESSAGES_NO_PERMISSION.toString(), true);
                        return;
                    }

                    if (ToggleCommand.insc.contains(player.getUniqueId())) {
                        ToggleCommand.insc.remove(player.getUniqueId());
                        Color.sendMessage(player, Config.MESSAGES_TOGGLE_OFF.toString(), true);
                    } else {
                        ToggleCommand.insc.add(player.getUniqueId());
                        Color.sendMessage(player, Config.MESSAGES_TOGGLE_ON.toString(), true);
                    }
                }
            } else {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(sender, "&c/staffchat <message>", true);
                    return;
                }

                Color.sendMessage(sender, "", false);
                Color.sendMessage(sender, "&c&lSimpleStaffChat2 %arrow2% Help", true);
                Color.sendMessage(sender, "", false);
                Color.sendMessage(sender, "&c/staffchat <message> - Send staffchat messages.", true);
                Color.sendMessage(sender, "&c/staffchattoggle - Send staffchat messages without needing to type a command.", true);
                Color.sendMessage(sender, "&c/adminchat <message> - Send adminchat messages.", true);
                Color.sendMessage(sender, "&c/adminchattoggle - Send adminchat messages without needing to type a command.", true);
                Color.sendMessage(sender, "&c/devchat <message> - Send devchat messages.", true);
                Color.sendMessage(sender, "&c/devchattoggle - Send devchat messages without needing to type a command.", true);
                Color.sendMessage(sender, "&c/staffchatreload - Reload the config file.", true);
                Color.sendMessage(sender, "", false);
            }
        }
    }
}