/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.listeners;

import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import me.refrac.simplestaffchat.bungee.utilities.Settings;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
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

        if (player.getUniqueId().toString().equalsIgnoreCase(Settings.getDevUUID)) {
            player.sendMessage(new TextComponent(" "));
            player.sendMessage(Color.format("&aWelcome " + Settings.getName + " Developer!"));
            player.sendMessage(Color.format("&aThis server is currently running " + Settings.getName + " &bv" + Settings.getVersion + "&a."));
            player.sendMessage(Color.format("&aPlugin name&7: &f" + Settings.getName));
            player.sendMessage(new TextComponent(" "));
            player.sendMessage(Color.format("&aServer version&7: &f" + ProxyServer.getInstance().getVersion()));
            player.sendMessage(new TextComponent(" "));
        }
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        if (event.getFrom() == null) return;
        if (Config.JOIN_ENABLED) return;
        if (!event.getPlayer().hasPermission("simplestaffchat.join")) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission("simplestaffchat.switch")) return;

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (!p.hasPermission("simplestaffchat.switch")) return;

            Color.sendMessage(player, Config.SWITCH_FORMAT.replace("%from%", event.getFrom().getName()), true, true);
        }
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        if (Config.JOIN_ENABLED) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission("simplestaffchat.quit")) return;

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (!p.hasPermission("simplestaffchat.quit")) return;

            Color.sendMessage(player, Config.QUIT_FORMAT, true, true);
        }
    }
}