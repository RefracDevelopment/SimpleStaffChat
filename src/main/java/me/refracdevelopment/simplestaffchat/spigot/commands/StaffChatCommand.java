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
package me.refracdevelopment.simplestaffchat.spigot.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.utilities.files.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("staffchat")
@Description("Send messages to your staff privately")
@CommandPermission(Permissions.STAFFCHAT_USE)
public class StaffChatCommand extends BaseCommand {

    @Dependency
    private SimpleStaffChat plugin;

    @Default
    public void onDefault(CommandSender sender, String[] args) {
        if (!Config.STAFFCHAT_ENABLED) return;

        if (args.length >= 1) {
            String format;
            String message = Joiner.on(" ").join(args);

            format = (sender instanceof Player) ? Config.STAFFCHAT_FORMAT.replace("%message%", message) :
             Config.CONSOLE_FORMAT.replace("%message%", message);

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(sender, format));
                }
            }
            plugin.getServer().getConsoleSender().sendMessage(Color.translate(sender, format));
        } else {
            if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("custom") && Config.STAFFCHAT_MESSAGE != null) {
                for (String s : Config.STAFFCHAT_MESSAGE)
                    Color.sendMessage(sender, s, true, true);
            } else if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                        for (String s : Config.STAFFCHAT_MESSAGE)
                            Color.sendMessage(sender, s, true, true);
                        return;
                    }

                    if (ToggleCommand.insc.contains(player.getUniqueId())) {
                        ToggleCommand.insc.remove(player.getUniqueId());
                        Color.sendMessage(player, Config.TOGGLE_OFF, true, true);
                    } else {
                        ToggleCommand.insc.add(player.getUniqueId());
                        Color.sendMessage(player, Config.TOGGLE_ON, true, true);
                    }
                }
            } else {
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
    }
}