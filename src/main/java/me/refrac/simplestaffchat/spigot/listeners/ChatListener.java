/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot.listeners;

import me.refrac.simplestaffchat.spigot.commands.ToggleCommand;
import me.refrac.simplestaffchat.spigot.utilities.Files;
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
            String format = Files.getConfig().getString("format.minecraft-format")
                    .replace("%message%", message);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("simplestaffchat.use")) return;

                p.sendMessage(Color.translate(player, format));
            }
        } else if (event.getMessage().contains(Files.getConfig().getString("staffchat-symbol")) && player.hasPermission("simplestaffchat.symbol")) {
            if (event.getMessage().equalsIgnoreCase(Files.getConfig().getString("staffchat-symbol"))) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Files.getConfig().getString("format.minecraft-format")
                    .replace("%message%", message.replaceFirst(Files.getConfig().getString("staffchat-symbol"), ""));

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("simplestaffchat.use")) return;

                p.sendMessage(Color.translate(player, format));
            }
        }
    }
}