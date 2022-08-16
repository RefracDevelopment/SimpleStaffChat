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
package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.commands.staff.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.admin.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.dev.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener extends Manager implements Listener {

    public ChatListener(SimpleStaffChat plugin) {
        super(plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        // StaffChat
        if (ToggleCommand.insc.contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            event.setCancelled(true);

            if (!player.hasPermission(Permissions.STAFFCHAT_USE) && !player.hasPermission(Permissions.STAFFCHAT_SEE)) {
                ToggleCommand.insc.remove(player.getUniqueId());
                Color.sendMessage(player, Config.MESSAGES_TOGGLE_OFF.toString(), true, true);
                return;
            }

            String message = event.getMessage();
            String format = Config.FORMAT_STAFFCHAT.toString().replace("%message%", message);

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            });
            plugin.getServer().getConsoleSender().sendMessage(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.STAFFCHAT_SYMBOL.toString()) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.STAFFCHAT_SYMBOL.toString())) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.FORMAT_STAFFCHAT.toString().replace("%message%", message
                    .replaceFirst(Config.STAFFCHAT_SYMBOL.toString(), ""));

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            });
            plugin.getServer().getConsoleSender().sendMessage(Color.translate(player, format));
        }

        // AdminChat
        if (AdminToggleCommand.inac.contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            event.setCancelled(true);

            if (!player.hasPermission(Permissions.ADMINCHAT_USE) && !player.hasPermission(Permissions.ADMINCHAT_SEE)) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                Color.sendMessage(player, Config.MESSAGES_ADMINCHAT_TOGGLE_OFF.toString(), true, true);
                return;
            }

            String message = event.getMessage();
            String format = Config.FORMAT_ADMINCHAT.toString().replace("%message%", message);

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            });
            plugin.getServer().getConsoleSender().sendMessage(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.ADMINCHAT_SYMBOL.toString()) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.ADMINCHAT_SYMBOL.toString())) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.FORMAT_ADMINCHAT.toString().replace("%message%", message
                    .replaceFirst(Config.ADMINCHAT_SYMBOL.toString(), ""));

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            });
            plugin.getServer().getConsoleSender().sendMessage(Color.translate(player, format));
        }

        // DevChat
        if (DevToggleCommand.indc.contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            event.setCancelled(true);

            if (!player.hasPermission(Permissions.DEVCHAT_USE) && !player.hasPermission(Permissions.DEVCHAT_SEE)) {
                DevToggleCommand.indc.remove(player.getUniqueId());
                Color.sendMessage(player, Config.MESSAGES_DEVCHAT_TOGGLE_OFF.toString(), true, true);
                return;
            }

            String message = event.getMessage();
            String format = Config.FORMAT_DEVCHAT.toString().replace("%message%", message);

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            });
            plugin.getServer().getConsoleSender().sendMessage(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.DEVCHAT_SYMBOL.toString()) && player.hasPermission(Permissions.DEVCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.DEVCHAT_SYMBOL.toString())) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.FORMAT_DEVCHAT.toString().replace("%message%", message
                    .replaceFirst(Config.DEVCHAT_SYMBOL.toString(), ""));

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            });
            plugin.getServer().getConsoleSender().sendMessage(Color.translate(player, format));
        }
    }
}