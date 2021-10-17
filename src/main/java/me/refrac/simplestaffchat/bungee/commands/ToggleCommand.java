/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.commands;

import me.refrac.simplestaffchat.bungee.utilities.Files;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ToggleCommand extends Command {
    public static List<UUID> insc = new ArrayList<>();

    public ToggleCommand() {
        super("staffchattoggle", "simplestaffchat.toggle", "sctoggle");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("toggle")) {
                if (insc.contains(player.getUniqueId())) {
                    insc.remove(player.getUniqueId());
                    player.sendMessage(Color.translate(player, Files.getConfig().getString("messages.toggle-off")));
                } else {
                    insc.add(player.getUniqueId());
                    player.sendMessage(Color.translate(player, Files.getConfig().getString("messages.toggle-on")));
                }
            }
        }
    }
}