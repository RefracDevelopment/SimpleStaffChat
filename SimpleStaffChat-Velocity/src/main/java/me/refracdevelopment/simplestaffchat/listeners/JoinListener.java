package me.refracdevelopment.simplestaffchat.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.utilities.JoinType;
import me.refracdevelopment.simplestaffchat.utilities.Permissions;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;

import java.util.UUID;

public class JoinListener {

    private final UUID getDevUUID = UUID.fromString("d9c670ed-d7d5-45fb-a144-8b8be86c4a2d");
    private final UUID getDevUUID2 = UUID.fromString("ab898e40-9088-45eb-9d69-e0b78e872627");

    @Subscribe
    public void onJoin(ServerPostConnectEvent event) {
        Player player = event.getPlayer();

        if (player.getUniqueId().equals(getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(getDevUUID2)) {
            sendDevMessage(player);
        }

        if (!player.hasPermission(Permissions.STAFFCHAT_JOIN))
            return;
        if (event.getPreviousServer() != null)
            return;
        if (!player.getCurrentServer().isPresent())
            return;
        if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName()))
            return;
        if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName()))
            return;

        SimpleStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                p.sendMessage(Color.translate(player, SimpleStaffChat.getInstance().getConfig().JOIN_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
            }
        });

        Color.log(SimpleStaffChat.getInstance().getConfig().JOIN_FORMAT
                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                .replace("%player%", player.getUsername())
        );

        DiscordImpl.sendJoin(JoinType.JOIN, player, player.getCurrentServer().get().getServerInfo().getName(), "");
    }

    @Subscribe
    public void onSwitch(ServerPostConnectEvent event) {
        if (event.getPreviousServer() == null)
            return;

        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_SWITCH))
            return;
        if (!player.getCurrentServer().isPresent())
            return;
        if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName()))
            return;

        SimpleStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                p.sendMessage(Color.translate(player, SimpleStaffChat.getInstance().getConfig().SWITCH_FORMAT
                        .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                        .replace("%from%", event.getPreviousServer().getServerInfo().getName())
                        .replace("%player%", player.getUsername())
                ));
            }
        });

        Color.log(SimpleStaffChat.getInstance().getConfig().SWITCH_FORMAT
                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                .replace("%from%", event.getPreviousServer().getServerInfo().getName())
                .replace("%player%", player.getUsername())
        );

        DiscordImpl.sendJoin(JoinType.SWITCH, player, player.getCurrentServer().get().getServerInfo().getName(), event.getPreviousServer().getServerInfo().getName());
    }

    @Subscribe
    public void onQuit(DisconnectEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT))
            return;
        if (!player.getCurrentServer().isPresent())
            return;
        if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName()))
            return;

        SimpleStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                p.sendMessage(Color.translate(player, SimpleStaffChat.getInstance().getConfig().QUIT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
            }
        });

        Color.log(SimpleStaffChat.getInstance().getConfig().QUIT_FORMAT
                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                .replace("%player%", player.getUsername())
        );

        DiscordImpl.sendJoin(JoinType.LEAVE, player, player.getCurrentServer().get().getServerInfo().getName(), "");
    }

    private void sendDevMessage(Player player) {
        PluginContainer container = SimpleStaffChat.getInstance().getServer().getPluginManager().getPlugin("simplestaffchat").get();

        Color.sendCustomMessage(player, " ");
        Color.sendCustomMessage(player, "<green>Welcome " + container.getDescription().getName().get() + " Developer!");
        Color.sendCustomMessage(player, "<green>This server is currently running " + container.getDescription().getName().get() + " <aqua>v" + container.getDescription().getVersion().get() + "<green>.");
        Color.sendCustomMessage(player, "<green>Plugin name<gray>: <white>" + container.getDescription().getName().get());
        Color.sendCustomMessage(player, " ");
        Color.sendCustomMessage(player, "<green>Server version<gray>: <white>" + SimpleStaffChat.getInstance().getServer().getVersion());
        Color.sendCustomMessage(player, " ");
    }
}