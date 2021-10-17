/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot.commands;

import me.refrac.simplestaffchat.spigot.utilities.Files;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ToggleCommand implements CommandExecutor {
    public static List<UUID> insc = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (args.length == 0) {
            if (!player.hasPermission("simplestaffchat.toggle")) {
                player.sendMessage(Color.translate(player, Files.getConfig().getString("messages.no-permission")));
                return true;
            }

            if (insc.contains(player.getUniqueId())) {
                insc.remove(player.getUniqueId());
                player.sendMessage(Color.translate(player, Files.getConfig().getString("messages.toggle-off")));
            } else {
                insc.add(player.getUniqueId());
                player.sendMessage(Color.translate(player, Files.getConfig().getString("messages.toggle-on")));
            }
            return true;
        }
        return false;
    }
}