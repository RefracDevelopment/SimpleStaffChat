/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot.listeners;

import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.spigot.commands.ToggleCommand;
import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (ToggleCommand.insc.contains(player.getUniqueId())) {
            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.STAFFCHAT_FORMAT.replace("%message%", message);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

                p.sendMessage(Color.translate(player, format));
            }
        } else if (event.getMessage().contains(Config.STAFFCHAT_SYMBOL) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.STAFFCHAT_SYMBOL)) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.STAFFCHAT_FORMAT.replace("%message%", message.replaceFirst(Config.STAFFCHAT_SYMBOL, ""));

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

                p.sendMessage(Color.translate(player, format));
            }
        }
    }
}