/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.spigot.commands;

import com.google.common.base.Joiner;
import me.refrac.simplestaffchat.shared.Settings;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;
import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.spigot.utilities.files.Config;
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
            if (!player.hasPermission(Permissions.STAFFCHAT_USE)) {
                Color.sendMessage(player, Config.NO_PERMISSION, true, false);
                return true;
            }

            String message = Joiner.on(" ").join(args);
            String format = Config.STAFFCHAT_FORMAT.replace("%message%", message);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return true;

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
        return true;
    }
}