/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 RefracDevelopment
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            }
        } else {
            if (!player.hasPermission(Permissions.STAFFCHAT_USE)) {
                Color.sendMessage(player, Config.NO_PERMISSION, true, false);
                return true;
            }

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