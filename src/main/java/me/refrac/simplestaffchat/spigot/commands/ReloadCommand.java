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

import me.refrac.simplestaffchat.spigot.SimpleStaffChat;
import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import me.refrac.simplestaffchat.spigot.utilities.files.Files;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission(Permissions.STAFFCHAT_ADMIN)) {
                Color.sendMessage(player, Config.NO_PERMISSION, true, false);
                return true;
            }

            Files.reloadFiles(SimpleStaffChat.getInstance());
            Config.loadConfig();
            Color.sendMessage(player, Config.RELOAD, true, true);
        } else {
            if (!sender.hasPermission(Permissions.STAFFCHAT_ADMIN)) {
                Color.sendMessage(sender, Config.NO_PERMISSION, true);
                return true;
            }

            Files.reloadFiles(SimpleStaffChat.getInstance());
            Config.loadConfig();
            Color.sendMessage(sender, Config.RELOAD, true);
        }
        return true;
    }
}