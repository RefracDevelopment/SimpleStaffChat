/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.commands;

import com.google.common.base.Joiner;
import me.refrac.simplestaffchat.bungee.utilities.Files;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    public StaffChatCommand() {
        super("staffchat", "simplestaffchat.use", "sc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length >= 1) {
            String message = Joiner.on(" ").join(args);
            String format = Files.getConfig().getString("format.minecraft-format")
                    .replace("%message%", message);

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (!p.hasPermission("simplestaffchat.use")) return;

                p.sendMessage(Color.translate(player, format));
            }
        } else {
            sender.sendMessage(Color.format("&e&lUsage: /staffchat <message>"));
            sender.sendMessage(Color.format("&e&lUsage: /staffchattoggle"));
            sender.sendMessage(Color.format(""));
            sender.sendMessage(Color.format("&e&lToggle: "));
        }
    }
}