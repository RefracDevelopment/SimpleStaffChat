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
        if (!Config.STAFFCHAT_ENABLED) return false;

        if (!sender.hasPermission(Permissions.STAFFCHAT_USE)) {
            Color.sendMessage(sender, Config.NO_PERMISSION, true, false);
            return true;
        }

        if (args.length >= 1) {

            String format;
            String message = Joiner.on(" ").join(args);
            format = (sender instanceof Player) ? Config.STAFFCHAT_FORMAT.replace("%message%", message) :
             Config.CONSOLE_FORMAT.replace("%message%", message);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(sender, format));
                }
            }

            Bukkit.getConsoleSender().sendMessage(Color.translate(sender, format));
        } else {
            Color.sendMessage(sender, "", false, false);
            Color.sendMessage(sender, "&e&lRunning " + Settings.getName + " &bv" + Settings.getVersion, true, false);
            Color.sendMessage(sender, "", false, false);
            Color.sendMessage(sender, "&e&lUsage: /staffchat <message>", true, false);
            Color.sendMessage(sender, "&e&lUsage: /staffchattoggle", true, false);
            Color.sendMessage(sender, "&e&lUsage: /staffchatreload", true, false);
            Color.sendMessage(sender, "", false, false);
        }
        return true;
    }
}