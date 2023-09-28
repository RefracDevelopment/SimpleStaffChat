package me.refracdevelopment.simplestaffchat.bungee.listeners;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.shared.Settings;
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
            if (!player.hasPermission(Permissions.STAFFCHAT_JOIN)) return;
            if (event.getReason() == ServerConnectEvent.Reason.JOIN_PROXY) return;

            ProxyServer.getInstance().getPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, Config.JOIN_FORMAT.replace("%server%", event.getTarget().getName())));
                }
            });
            Color.log2(Color.translate(player, Config.JOIN_FORMAT.replace("%server%", event.getTarget().getName())));
        }

        if (player.getUniqueId().equals(Settings.getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(Settings.getDevUUID2)) {
            sendDevMessage(player);
        }
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        if (event.getFrom() == null) return;
        if (!Config.JOIN_ENABLED) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_SWITCH)) return;

        ProxyServer.getInstance().getPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(player, Config.SWITCH_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                        .replace("%from%", event.getFrom().getName())));
            }
        });
        Color.log2(Color.translate(player, Config.SWITCH_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                .replace("%from%", event.getFrom().getName())));
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        if (!Config.JOIN_ENABLED) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT)) return;
        if (player.getServer() == null) return;

        ProxyServer.getInstance().getPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(player, Config.QUIT_FORMAT.replace("%server%", player.getServer().getInfo().getName())));
            }
        });
        Color.log2(Color.translate(player, Config.QUIT_FORMAT.replace("%server%", player.getServer().getInfo().getName())));
    }

    private void sendDevMessage(ProxiedPlayer player) {
        Color.sendMessage(player, " ");
        Color.sendMessage(player, "&aWelcome " + BungeeStaffChat.getInstance().getDescription().getName() + " Developer!");
        Color.sendMessage(player, "&aThis server is currently running " + BungeeStaffChat.getInstance().getDescription().getName() + " &bv" + BungeeStaffChat.getInstance().getDescription().getVersion() + "&a.");
        Color.sendMessage(player, "&aPlugin name&7: &f" + BungeeStaffChat.getInstance().getDescription().getName());
        Color.sendMessage(player, " ");
        Color.sendMessage(player, "&aServer version&7: &f" + ProxyServer.getInstance().getVersion());
        Color.sendMessage(player, " ");
    }
}