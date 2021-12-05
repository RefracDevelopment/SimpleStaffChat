/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.listeners;

import me.refrac.simplestaffchat.bungee.commands.ToggleCommand;
import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import me.refrac.simplestaffchat.bungee.utilities.files.Files;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import me.refrac.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (ToggleCommand.insc.contains(player.getUniqueId())) {
            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.STAFFCHAT_FORMAT.replace("%message%", message);

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

                p.sendMessage(Color.translate(player, format));
            }
        } else if (event.getMessage().contains(Config.STAFFCHAT_SYMBOL) &&
                player.hasPermission(Permissions.STAFFCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.STAFFCHAT_SYMBOL)) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Files.getConfig().getString("format.minecraft-format")
                    .replace("%message%", message.replaceFirst(Config.STAFFCHAT_SYMBOL, ""));

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

                p.sendMessage(Color.translate(player, format));
            }
        }
    }
}