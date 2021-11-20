/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot.commands;

import com.google.common.base.Joiner;
import me.refrac.simplestaffchat.spigot.utilities.Settings;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;
import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import me.refrac.simplestaffchat.spigot.utilities.files.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length >= 1) {
            if (!player.hasPermission("simplestaffchat.use")) {
                player.sendMessage(Color.translate(player, Files.getConfig().getString("messages.no-permission")));
                return true;
            }

            String message = Joiner.on(" ").join(args);
            String format = Config.STAFFCHAT_FORMAT.replace("%message%", message);

            Bukkit.broadcast(Color.translate(player, format), "simplestaffchat.see");
        } else {
            player.sendMessage(" ");
            player.sendMessage(Color.translate(player, "&e&lRunning " + Settings.getName + " v" + Settings.getVersion));
            player.sendMessage(" ");
            player.sendMessage(Color.translate(player, "&e&lUsage: /staffchat <message>"));
            player.sendMessage(Color.translate(player, "&e&lUsage: /staffchattoggle"));
            player.sendMessage(" ");
        }
        return false;
    }
}