/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.bungee.commands;

import com.google.common.base.Joiner;
import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.shared.Settings;
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
            if (!player.hasPermission(Permissions.STAFFCHAT_USE)) {
                Color.sendMessage(player, Config.NO_PERMISSION, true);
                return;
            }

            String message = Joiner.on(" ").join(args);
            String format = Config.STAFFCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message);

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

                p.sendMessage(Color.translate(player, format));
            }
        } else {
            Color.sendMessage(player, "", true);
            Color.sendMessage(player, "&e&lRunning " + Settings.getName + " &bv" + Settings.getVersion, true);
            Color.sendMessage(player, "", true);
            Color.sendMessage(player, "&e&lUsage: /sc <message>", true);
            Color.sendMessage(player, "&e&lUsage: /sctoggle", true);
            Color.sendMessage(player, "", true);
        }
    }
}