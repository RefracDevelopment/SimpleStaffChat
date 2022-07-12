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
package me.refrac.simplestaffchat.spigot.listeners;

import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.spigot.SimpleStaffChat;
import me.refrac.simplestaffchat.spigot.commands.ToggleCommand;
import me.refrac.simplestaffchat.spigot.utilities.Manager;
import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import me.refrac.simplestaffchat.spigot.utilities.files.Files;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreprocessListener extends Manager implements Listener {

    public CommandPreprocessListener(SimpleStaffChat plugin) {
        super(plugin);
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        for (String alias : Config.STAFFCHAT_ALIAS) {
            String[] args = event.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("/" + alias)) {
                if (!Config.STAFFCHAT_ENABLED) return;
                event.setCancelled(true);
                String message = event.getMessage().replaceFirst("/" + alias + " ", "");
                String format = Config.STAFFCHAT_FORMAT.replace("%message%", message);

                if (player.hasPermission(Permissions.STAFFCHAT_USE)) {
                    if (args.length >= 2) {
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

                            p.sendMessage(Color.translate(player, format));
                        }
                        plugin.getServer().getConsoleSender().sendMessage(Color.translate(player, format));
                    } else {
                        if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("custom")) {
                            for (String s : Config.STAFFCHAT_MESSAGE)
                                Color.sendMessage(player, s, true, true);
                        } else if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                            if (player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                                if (ToggleCommand.insc.contains(player.getUniqueId())) {
                                    ToggleCommand.insc.remove(player.getUniqueId());
                                    Color.sendMessage(player, Config.TOGGLE_OFF, true, true);
                                } else {
                                    ToggleCommand.insc.add(player.getUniqueId());
                                    Color.sendMessage(player, Config.TOGGLE_ON, true, true);
                                }
                            }
                        } else {
                            Color.sendMessage(player, "", false, false);
                            Color.sendMessage(player, "&c&lSimpleStaffChat %arrow2% Help", true, true);
                            Color.sendMessage(player, "", false, false);
                            Color.sendMessage(player, "&c/staffchat <message> - Send staffchat messages.", true, true);
                            Color.sendMessage(player, "&c/staffchattoggle - Send staffchat messages without needing to type a command.", true, true);
                            Color.sendMessage(player, "&c/staffchatreload - Reload the config file.", true, true);
                            Color.sendMessage(player, "", false, false);
                        }
                    }
                } else {
                    Color.sendMessage(player, Config.NO_PERMISSION, true, true);
                    return;
                }
            }
        }

        for (String alias : Config.TOGGLE_ALIAS) {
            String[] args = event.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("/" + alias)) {
                if (!Config.TOGGLE_ENABLED) return;
                event.setCancelled(true);
                if (player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                    if (ToggleCommand.insc.contains(player.getUniqueId())) {
                        ToggleCommand.insc.remove(player.getUniqueId());
                        Color.sendMessage(player, Config.TOGGLE_OFF, true, true);
                    } else {
                        ToggleCommand.insc.add(player.getUniqueId());
                        Color.sendMessage(player, Config.TOGGLE_ON, true, true);
                    }
                } else {
                    Color.sendMessage(player, Config.NO_PERMISSION, true, true);
                    return;
                }
            }
        }

        for (String alias : Config.RELOAD_ALIAS) {
            String[] args = event.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("/" + alias)) {
                if (!Config.RELOAD_ENABLED) return;
                event.setCancelled(true);
                if (player.hasPermission(Permissions.STAFFCHAT_ADMIN)) {
                    Files.reloadFiles(plugin);
                    Color.sendMessage(player, Config.RELOAD, true, true);
                } else {
                    Color.sendMessage(player, Config.NO_PERMISSION, true, true);
                    return;
                }
            }
        }
    }
}
