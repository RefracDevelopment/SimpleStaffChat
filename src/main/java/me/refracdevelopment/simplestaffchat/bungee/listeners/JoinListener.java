package me.refracdevelopment.simplestaffchat.bungee.listeners;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class JoinListener implements Listener {

    private BungeeStaffChat plugin;

    private final UUID getDevUUID = UUID.fromString("d9c670ed-d7d5-45fb-a144-8b8be86c4a2d");
    private final UUID getDevUUID2 = UUID.fromString("ab898e40-9088-45eb-9d69-e0b78e872627");
    
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
        if (!player.hasPermission(plugin.getPermissions().STAFFCHAT_JOIN)) return;
        if (event.getReason() != ServerConnectEvent.Reason.JOIN_PROXY) return;

        plugin.getProxy().getPlayers().forEach(p -> {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(player, plugin.getConfig().JOIN_FORMAT
                        .replace("%server%", event.getTarget().getName())));
            }
        });
        plugin.getColor().log2(plugin.getColor().translate(player, plugin.getConfig().JOIN_FORMAT
                .replace("%server%", event.getTarget().getName())));
        plugin.getDiscordImpl().sendJoin(player, JoinType.JOIN, plugin.getConfig().JOIN_FORMAT
                .replace("%server%", event.getTarget().getName())
                .replace("%player%", player.getName())
        );
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        if (event.getFrom() == null) return;
        if (!plugin.getConfig().JOIN_ENABLED) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(plugin.getPermissions().STAFFCHAT_SWITCH)) return;

        plugin.getProxy().getPlayers().forEach(p -> {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(player, plugin.getConfig().SWITCH_FORMAT
                        .replace("%server%", player.getServer().getInfo().getName())
                        .replace("%from%", event.getFrom().getName())));
            }
        });
        plugin.getColor().log2(plugin.getColor().translate(player, plugin.getConfig().SWITCH_FORMAT
                .replace("%server%", player.getServer().getInfo().getName())
                .replace("%from%", event.getFrom().getName())));
        plugin.getDiscordImpl().sendJoin(player, JoinType.SWITCH, plugin.getConfig().SWITCH_FORMAT
                .replace("%server%", player.getServer().getInfo().getName())
                .replace("%from%", event.getFrom().getName())
                .replace("%player%", player.getName())
                .replace("%arrow%", "\u00BB")
        );
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        if (!plugin.getConfig().JOIN_ENABLED) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(plugin.getPermissions().STAFFCHAT_QUIT)) return;
        if (player.getServer() == null) return;

        plugin.getProxy().getPlayers().forEach(p -> {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(player, plugin.getConfig().QUIT_FORMAT
                        .replace("%server%", player.getServer().getInfo().getName())));
            }
        });
        plugin.getColor().log2(plugin.getColor().translate(player, plugin.getConfig().QUIT_FORMAT
                .replace("%server%", player.getServer().getInfo().getName())));
        plugin.getDiscordImpl().sendJoin(player, JoinType.LEAVE, plugin.getConfig().QUIT_FORMAT
                .replace("%server%", player.getServer().getInfo().getName())
                .replace("%player%", player.getName())
        );
    }

    private void sendDevMessage(ProxiedPlayer player) {
        plugin.getColor().sendMessage(player, " ");
        plugin.getColor().sendMessage(player, "&aWelcome " + plugin.getDescription().getName() + " Developer!");
        plugin.getColor().sendMessage(player, "&aThis server is currently running " + plugin.getDescription().getName() + " &bv" + plugin.getDescription().getVersion() + "&a.");
        plugin.getColor().sendMessage(player, "&aPlugin name&7: &f" + plugin.getDescription().getName());
        plugin.getColor().sendMessage(player, " ");
        plugin.getColor().sendMessage(player, "&aServer version&7: &f" + plugin.getProxy().getVersion());
        plugin.getColor().sendMessage(player, " ");
    }
}