/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.listeners;

import me.refrac.simplestaffchat.bungee.BungeeStaffChat;
import me.refrac.simplestaffchat.bungee.utilities.Files;
import me.refrac.simplestaffchat.bungee.utilities.Settings;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import me.refrac.simplestaffchat.bungee.utilities.updatechecker.UpdateChecker;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (Files.getConfig().getBoolean("update.enabled")) {
            if (!player.hasPermission("simplestaffchat.update")) return;

            new UpdateChecker(BungeeStaffChat.getInstance(), 91883).getLatestVersion(version -> {
                if (!BungeeStaffChat.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                    player.sendMessage(Color.format("&7&m-----------------------------------------"));
                    player.sendMessage(Color.format("&bA new version of " + Settings.getName + " &bhas been released!"));
                    player.sendMessage(Color.format("&bPlease update here: &e" + Settings.getPluginURL));
                    player.sendMessage(Color.format("&7&m-----------------------------------------"));
                }
            });
        }

        if (Files.getConfig().getBoolean("join.enabled")) {
            if (!player.hasPermission("simplestaffchat.join")) return;
            if (player.getServer() != null) return;

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (!p.hasPermission("simplestaffchat.join")) return;

                p.sendMessage(Color.translate(player, Files.getConfig().getString("join.join-format")
                        .replace("%server%", event.getTarget().getName())));
            }
        }

        if (player.getName().equalsIgnoreCase("Refracxx")) {
            player.sendMessage(" ");
            player.sendMessage(Color.format("&aWelcome " + Settings.getName + " Developer!"));
            player.sendMessage(Color.format("&aThis server is currently running " + Settings.getName + " &bv" + Settings.getVersion + "&a."));
            player.sendMessage(Color.format("&aPlugin name&7: &f" + Settings.getName));
            player.sendMessage(" ");
            player.sendMessage(Color.format("&aServer version&7: &f" + ProxyServer.getInstance().getVersion()));
            player.sendMessage(" ");
        }
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        if (event.getFrom() == null) return;
        if (!Files.getConfig().getBoolean("join.enabled")) return;
        if (!event.getPlayer().hasPermission("simplestaffchat.join")) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission("simplestaffchat.switch")) return;

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (!p.hasPermission("simplestaffchat.switch")) return;

            p.sendMessage(Color.translate(player, Files.getConfig().getString("join.switch-format")
                    .replace("%from%", event.getFrom().getName())));
        }
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        if (!Files.getConfig().getBoolean("join.enabled")) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission("simplestaffchat.quit")) return;

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (!p.hasPermission("simplestaffchat.quit")) return;

            p.sendMessage(Color.translate(player, Files.getConfig().getString("join.quit-format")));
        }
    }
}