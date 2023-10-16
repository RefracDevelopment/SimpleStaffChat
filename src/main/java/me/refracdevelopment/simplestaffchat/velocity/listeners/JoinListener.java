package me.refracdevelopment.simplestaffchat.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Manager;

import java.util.UUID;

public class JoinListener extends Manager {

    private final UUID getDevUUID = UUID.fromString("d9c670ed-d7d5-45fb-a144-8b8be86c4a2d");
    private final UUID getDevUUID2 = UUID.fromString("ab898e40-9088-45eb-9d69-e0b78e872627");

    public JoinListener(VelocityStaffChat plugin) {
        super(plugin);
    }

    @Subscribe
    public void onJoin(ServerPostConnectEvent event) {
        Player player = event.getPlayer();

        if (player.getUniqueId().equals(getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(getDevUUID2)) {
            sendDevMessage(player);
        }

        if (!plugin.getConfig().JOIN_ENABLED) return;
        if (!player.hasPermission(plugin.getPermissions().STAFFCHAT_JOIN)) return;
        if (event.getPreviousServer() != null) return;
        if (!player.getCurrentServer().isPresent()) return;

        plugin.getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(player, plugin.getConfig().JOIN_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
            }
        });
        plugin.getColor().log2(plugin.getColor().translate(player, plugin.getConfig().JOIN_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
        plugin.getDiscordImpl().sendJoin(JoinType.JOIN, player, player.getCurrentServer().get().getServerInfo().getName(), "");
    }

    @Subscribe
    public void onSwitch(ServerPostConnectEvent event) {
        if (!plugin.getConfig().JOIN_ENABLED) return;
        if (event.getPreviousServer() == null) return;

        Player player = event.getPlayer();

        if (!player.hasPermission(plugin.getPermissions().STAFFCHAT_SWITCH)) return;
        if (!player.getCurrentServer().isPresent()) return;

        plugin.getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(player, plugin.getConfig().SWITCH_FORMAT.replace("%server%", player.getCurrentServer().get().getServer().getServerInfo().getName())
                        .replace("%from%", event.getPreviousServer().getServerInfo().getName())));
            }
        });
        plugin.getColor().log2(plugin.getColor().translate(player, plugin.getConfig().SWITCH_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                .replace("%from%", event.getPreviousServer().getServerInfo().getName())));
        plugin.getDiscordImpl().sendJoin(JoinType.SWITCH, player, player.getCurrentServer().get().getServerInfo().getName(), event.getPreviousServer().getServerInfo().getName());
    }

    @Subscribe
    public void onQuit(DisconnectEvent event) {
        if (!plugin.getConfig().JOIN_ENABLED) return;

        Player player = event.getPlayer();

        if (!player.hasPermission(plugin.getPermissions().STAFFCHAT_QUIT)) return;

        plugin.getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(player, plugin.getConfig().QUIT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
            }
        });
        plugin.getColor().log2(plugin.getColor().translate(player, plugin.getConfig().QUIT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
        plugin.getDiscordImpl().sendJoin(JoinType.LEAVE, player, player.getCurrentServer().get().getServerInfo().getName(), "");
    }

    private void sendDevMessage(Player player) {
        plugin.getColor().sendMessage(player, " ");
        plugin.getColor().sendMessage(player, "<green>Welcome " + plugin.getContainer().getDescription().getName().get() + " Developer!");
        plugin.getColor().sendMessage(player, "<green>This server is currently running " + plugin.getContainer().getDescription().getName().get() + " <aqua>v" + plugin.getContainer().getDescription().getVersion().get() + "<green>.");
        plugin.getColor().sendMessage(player, "<green>Plugin name<gray>: <white>" + plugin.getContainer().getDescription().getName().get());
        plugin.getColor().sendMessage(player, " ");
        plugin.getColor().sendMessage(player, "<green>Server version<gray>: <white>" + plugin.getServer().getVersion());
        plugin.getColor().sendMessage(player, " ");
    }
}