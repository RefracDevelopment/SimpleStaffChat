/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.commands;

import com.google.common.base.Joiner;
import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    public StaffChatCommand() {
        super("staffchat", "", "sc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length >= 1) {
            if (!player.hasPermission("simplestaffchat.use")) {
                Color.sendMessage(player, Config.NO_PERMISSION, true);
                return;
            }

            String message = Joiner.on(" ").join(args);
            String format = Config.STAFFCHAT_FORMAT.replace("%message%", message);

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (!p.hasPermission("simplestaffchat.see")) return;

                Color.sendMessage(player, format, true, true);
            }
        } else {
            Color.sendMessage(player, "", true);
            Color.sendMessage(player, "&e&lUsage: /staffchat <message>", true);
            Color.sendMessage(player, "&e&lUsage: /staffchattoggle", true);
            Color.sendMessage(player, "", true);
        }
    }
}