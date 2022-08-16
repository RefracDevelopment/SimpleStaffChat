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
package me.refracdevelopment.simplestaffchat.spigot.commands.admin;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.commands.Command;
import me.refracdevelopment.simplestaffchat.spigot.commands.dev.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.staff.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminChatCommand extends Command {

    public AdminChatCommand() {
        super(Config.COMMANDS_ADMINCHAT_COMMAND.toString(), Config.COMMANDS_ADMINCHAT_ALIASES.toList());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!Config.COMMANDS_ADMINCHAT_ENABLED.toBoolean()) return true;

        if (!sender.hasPermission(Permissions.ADMINCHAT_USE)) {
            Color.sendMessage(sender, Config.MESSAGES_NO_PERMISSION.toString(), true, true);
            return true;
        }

        if (args.length >= 1) {
            String format;
            String message = Joiner.on(" ").join(args);

            format = (sender instanceof Player) ? Config.FORMAT_ADMINCHAT.toString().replace("%message%", message) :
                    Config.CONSOLE_ADMINCHAT.toString().replace("%message%", message);

            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    p.sendMessage(Color.translate(sender, format));
                }
            }
            Bukkit.getServer().getConsoleSender().sendMessage(Color.translate(sender, format));
        } else {
            if (Config.MESSAGES_STAFFCHAT_OUTPUT.toString().equalsIgnoreCase("toggle")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
                        Color.sendMessage(sender, Config.MESSAGES_NO_PERMISSION.toString(), true, true);
                        return true;
                    }

                    if (AdminToggleCommand.inac.contains(player.getUniqueId())) {
                        AdminToggleCommand.inac.remove(player.getUniqueId());
                        Color.sendMessage(player, Config.MESSAGES_ADMINCHAT_TOGGLE_OFF.toString(), true, true);
                    } else {
                        if (ToggleCommand.insc.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                            ToggleCommand.insc.remove(player.getUniqueId());
                            DevToggleCommand.indc.remove(player.getUniqueId());
                        }
                        AdminToggleCommand.inac.add(player.getUniqueId());
                        Color.sendMessage(player, Config.MESSAGES_ADMINCHAT_TOGGLE_ON.toString(), true, true);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}
