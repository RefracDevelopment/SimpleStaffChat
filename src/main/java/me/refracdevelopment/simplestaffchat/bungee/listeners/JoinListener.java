package me.refracdevelopment.simplestaffchat.bungee.listeners;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class JoinListener implements Listener {

    private final BungeeStaffChat plugin;

    protected final UUID getDevUUID = UUID.fromString("d9c670ed-d7d5-45fb-a144-8b8be86c4a2d");
    protected final UUID getDevUUID2 = UUID.fromString("ab898e40-9088-45eb-9d69-e0b78e872627");
    
    public JoinListener(BungeeStaffChat plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (player.getUniqueId().equals(getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(getDevUUID2)) {
            sendDevMessage(player);
        }

        if (!plugin.getConfig().JOIN_ENABLED) return;
        if (!player.hasPermission(Permissions.STAFFCHAT_JOIN)) return;
        if (event.getReason() != ServerConnectEvent.Reason.JOIN_PROXY) return;

        plugin.getProxy().getPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendCustomMessage(p, Color.translate(player, plugin.getConfig().JOIN_FORMAT
                        .replace("%server%", event.getTarget().getName())));
            }
        });
        Color.log2(Color.translate(player, plugin.getConfig().JOIN_FORMAT
                .replace("%server%", event.getTarget().getName())));
        DiscordImpl.sendJoin(JoinType.JOIN, player, player.getServer().getInfo().getName(), "");
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        if (event.getFrom() == null) return;
        if (!plugin.getConfig().JOIN_ENABLED) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_SWITCH)) return;

        plugin.getProxy().getPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendCustomMessage(p, Color.translate(player, plugin.getConfig().SWITCH_FORMAT
                        .replace("%server%", player.getServer().getInfo().getName())
                        .replace("%from%", event.getFrom().getName())));
            }
        });
        Color.log2(Color.translate(player, plugin.getConfig().SWITCH_FORMAT
                .replace("%server%", player.getServer().getInfo().getName())
                .replace("%from%", event.getFrom().getName())));
        DiscordImpl.sendJoin(JoinType.SWITCH, player, player.getServer().getInfo().getName(), event.getFrom().getName());
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        if (!plugin.getConfig().JOIN_ENABLED) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT)) return;
        if (player.getServer() == null) return;

        plugin.getProxy().getPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendCustomMessage(p, Color.translate(player, plugin.getConfig().QUIT_FORMAT
                        .replace("%server%", player.getServer().getInfo().getName())));
            }
        });
        Color.log2(Color.translate(player, plugin.getConfig().QUIT_FORMAT
                .replace("%server%", player.getServer().getInfo().getName())));
        DiscordImpl.sendJoin(JoinType.LEAVE, player, player.getServer().getInfo().getName(), "");
    }

    private void sendDevMessage(ProxiedPlayer player) {
        Color.sendCustomMessage(player, " ");
        Color.sendCustomMessage(player, "&aWelcome " + plugin.getDescription().getName() + " Developer!");
        Color.sendCustomMessage(player, "&aThis server is currently running " + plugin.getDescription().getName() + " &bv" + plugin.getDescription().getVersion() + "&a.");
        Color.sendCustomMessage(player, "&aPlugin name&7: &f" + plugin.getDescription().getName());
        Color.sendCustomMessage(player, " ");
        Color.sendCustomMessage(player, "&aServer version&7: &f" + plugin.getProxy().getVersion());
        Color.sendCustomMessage(player, " ");
    }
}