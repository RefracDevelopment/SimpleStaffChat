/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.commands;

import me.refrac.simplestaffchat.bungee.utilities.files.Config;
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
        super("staffchattoggle", "", "sctoggle");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!sender.hasPermission("simplestaffchat.toggle")) {
            Color.sendMessage(player, Config.NO_PERMISSION, true);
            return;
        }

        if (insc.contains(player.getUniqueId())) {
            insc.remove(player.getUniqueId());
            Color.sendMessage(player, Config.TOGGLE_OFF, true, true);
        } else {
            insc.add(player.getUniqueId());
            Color.sendMessage(player, Config.TOGGLE_ON, true, true);
        }
    }
}