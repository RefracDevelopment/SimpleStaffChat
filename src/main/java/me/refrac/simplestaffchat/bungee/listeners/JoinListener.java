/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.bungee.listeners;

import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.shared.Settings;
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

        if (Config.JOIN_ENABLED) {
            if (!player.hasPermission("simplestaffchat.join")) return;
            if (player.getServer() != null) return;

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (!p.hasPermission("simplestaffchat.join")) return;

                Color.sendMessage(player, Config.JOIN_FORMAT.replace("%server%", event.getTarget().getName()), true, true);
            }
        }

        if (!player.getName().equalsIgnoreCase("Refracxx")) return;

        Color.sendMessage(player, " ", true);
        Color.sendMessage(player, "&aWelcome " + Settings.getName + " Developer!", true);
        Color.sendMessage(player, "&aThis server is currently running " + Settings.getName + " &bv" + Settings.getVersion + "&a.", true);
        Color.sendMessage(player, "&aPlugin name&7: &f" + Settings.getName, true);
        Color.sendMessage(player, " ", true);
        Color.sendMessage(player, "&aServer version&7: &f" + ProxyServer.getInstance().getVersion(), true);
        Color.sendMessage(player, " ", true);
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        if (event.getFrom() == null) return;
        if (Config.JOIN_ENABLED) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_SWITCH)) return;

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

            p.sendMessage(Color.translate(player, Config.SWITCH_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%from%", event.getFrom().getName())));
        }
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        if (Config.JOIN_ENABLED) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT)) return;

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

            p.sendMessage(Color.translate(player, Config.QUIT_FORMAT.replace("%server%", player.getServer().getInfo().getName())));
        }
    }
}