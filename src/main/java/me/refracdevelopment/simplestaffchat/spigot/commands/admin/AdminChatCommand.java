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

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.spigot.utilities.files.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("adminchat")
@Description("Send messages to your admins privately")
@CommandPermission(Permissions.ADMINCHAT_USE)
public class AdminChatCommand extends BaseCommand {

    @Dependency
    private SimpleStaffChat plugin;

    @Default
    public void onDefault(CommandSender sender, String[] args) {
        if (!Config.ADMINCHAT_ENABLED) return;

        if (args.length >= 1) {
            String format;
            String message = Joiner.on(" ").join(args);

            format = (sender instanceof Player) ? Config.ADMINCHAT_FORMAT.replace("%message%", message) :
                    Config.CONSOLE_ADMINCHAT_FORMAT.replace("%message%", message);

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    p.sendMessage(Color.translate(sender, format));
                }
            }
            plugin.getServer().getConsoleSender().sendMessage(Color.translate(sender, format));
        } else {
            if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
                        Color.sendMessage(sender, Config.NO_PERMISSION, true, true);
                        return;
                    }

                    if (AdminToggleCommand.inac.contains(player.getUniqueId())) {
                        AdminToggleCommand.inac.remove(player.getUniqueId());
                        Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_OFF, true, true);
                    } else {
                        AdminToggleCommand.inac.add(player.getUniqueId());
                        Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_ON, true, true);
                    }
                }
            }
        }
    }
}
