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
package me.refrac.simplestaffchat.spigot.listeners;

import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.shared.Settings;
import me.refrac.simplestaffchat.spigot.SimpleStaffChat;
import me.refrac.simplestaffchat.spigot.commands.ToggleCommand;
import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import me.refrac.simplestaffchat.spigot.utilities.files.Files;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreprocessListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        for (String alias : Config.STAFFCHAT_ALIAS) {
            String[] args = event.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("/" + alias)) {
                if (!Config.STAFFCHAT_ENABLED) return;
                event.setCancelled(true);
                String message = event.getMessage().replaceAll("/" + alias + " ", "");
                String format = Config.STAFFCHAT_FORMAT.replace("%message%", message);

                if (player.hasPermission(Permissions.STAFFCHAT_USE)) {
                    if (args.length >= 2) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                                p.sendMessage(Color.translate(player, format));
                            }
                        }

                        Bukkit.getConsoleSender().sendMessage(Color.translate(player, format));
                    } else {
                        Color.sendMessage(player, "", true);
                        Color.sendMessage(player, "&e&lRunning " + Settings.getName + " &bv" + Settings.getVersion, true);
                        Color.sendMessage(player, "", true);
                        Color.sendMessage(player, "&e&lUsage: /staffchat <message>", true);
                        Color.sendMessage(player, "&e&lUsage: /staffchattoggle", true);
                        Color.sendMessage(player, "&e&lUsage: /staffchatreload", true);
                        Color.sendMessage(player, "", true);
                    }
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
                }
            }
        }

        for (String alias : Config.RELOAD_ALIAS) {
            String[] args = event.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("/" + alias)) {
                if (!Config.RELOAD_ENABLED) return;
                event.setCancelled(true);
                if (player.hasPermission(Permissions.STAFFCHAT_ADMIN)) {
                    Files.reloadFiles(SimpleStaffChat.getInstance());
                    Config.loadConfig();
                    Color.sendMessage(player, Config.RELOAD, true, true);
                }
            }
        }
    }
}
