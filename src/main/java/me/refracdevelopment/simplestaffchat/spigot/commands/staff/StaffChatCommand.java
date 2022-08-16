/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 RefracDevelopment
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
package me.refracdevelopment.simplestaffchat.spigot.commands.staff;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.spigot.commands.dev.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.Command;
import me.refracdevelopment.simplestaffchat.spigot.commands.admin.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand extends Command {

    public StaffChatCommand() {
        super(Config.COMMANDS_STAFFCHAT_COMMAND.toString(), Config.COMMANDS_STAFFCHAT_ALIASES.toList());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!Config.COMMANDS_STAFFCHAT_ENABLED.toBoolean()) return true;

        if (!sender.hasPermission(Permissions.STAFFCHAT_USE)) {
            Color.sendMessage(sender, Config.MESSAGES_NO_PERMISSION.toString(), true, true);
            return true;
        }

        if (args.length >= 1) {
            String format;
            String message = Joiner.on(" ").join(args);

            format = (sender instanceof Player) ? Config.FORMAT_STAFFCHAT.toString().replace("%message%", message) :
                    Config.CONSOLE_STAFFCHAT.toString().replace("%message%", message);

            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(sender, format));
                }
            }
            Bukkit.getServer().getConsoleSender().sendMessage(Color.translate(sender, format));
        } else {
            if (Config.MESSAGES_STAFFCHAT_OUTPUT.toString().equalsIgnoreCase("custom") && Config.MESSAGES_STAFFCHAT_MESSAGE.toList() != null) {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    getUsage();
                    return true;
                }

                for (String s : Config.MESSAGES_STAFFCHAT_MESSAGE.toList())
                    Color.sendMessage(sender, s, true, true);
            } else if (Config.MESSAGES_STAFFCHAT_OUTPUT.toString().equalsIgnoreCase("toggle")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                        for (String s : Config.MESSAGES_STAFFCHAT_MESSAGE.toList())
                            Color.sendMessage(sender, s, true, true);
                        return true;
                    }

                    if (ToggleCommand.insc.contains(player.getUniqueId())) {
                        ToggleCommand.insc.remove(player.getUniqueId());
                        Color.sendMessage(player, Config.MESSAGES_TOGGLE_OFF.toString(), true, true);
                    } else {
                        if (DevToggleCommand.indc.contains(player.getUniqueId()) || AdminToggleCommand.inac.contains(player.getUniqueId())) {
                            DevToggleCommand.indc.remove(player.getUniqueId());
                            AdminToggleCommand.inac.remove(player.getUniqueId());
                        }
                        ToggleCommand.insc.add(player.getUniqueId());
                        Color.sendMessage(player, Config.MESSAGES_TOGGLE_ON.toString(), true, true);
                    }
                }
            } else {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(sender, "&c/staffchat <message>", true, true);
                    return true;
                }

                Color.sendMessage(sender, "", false, false);
                Color.sendMessage(sender, "&c&lSimpleStaffChat2 %arrow2% Help", true, true);
                Color.sendMessage(sender, "", false, false);
                Color.sendMessage(sender, "&c/staffchat <message> - Send staffchat messages.", true, true);
                Color.sendMessage(sender, "&c/adminchat <message> - Send adminchat messages.", true, true);
                Color.sendMessage(sender, "&c/devchat <message> - Send devchat messages.", true, true);
                Color.sendMessage(sender, "&c/staffchattoggle - Send staffchat messages without needing to type a command.", true, true);
                Color.sendMessage(sender, "&c/adminchattoggle - Send adminchat messages without needing to type a command.", true, true);
                Color.sendMessage(sender, "&c/devchattoggle - Send devchat messages without needing to type a command.", true, true);
                Color.sendMessage(sender, "&c/staffchatreload - Reload the config file.", true, true);
                Color.sendMessage(sender, "", false, false);
            }
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}