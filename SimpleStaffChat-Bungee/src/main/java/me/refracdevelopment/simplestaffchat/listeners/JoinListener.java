package me.refracdevelopment.simplestaffchat.listeners;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.utilities.JoinType;
import me.refracdevelopment.simplestaffchat.utilities.Permissions;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.UUID;

public class JoinListener implements Listener {

    private final SimpleStaffChat plugin;

    public JoinListener(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(ServerConnectEvent event) {
        if (event.getReason() != ServerConnectEvent.Reason.JOIN_PROXY)
            return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_JOIN))
            return;

        RyMessageUtils.broadcast(player, Permissions.STAFFCHAT_SEE, plugin.getConfig().JOIN_FORMAT
                .replace("%player%", player.getName())
                .replace("%server%", event.getTarget().getName()));

        RyMessageUtils.sendConsole(true, plugin.getConfig().JOIN_FORMAT
                .replace("%player%", player.getName())
                .replace("%server%", event.getTarget().getName()));

        DiscordImpl.sendJoin(JoinType.JOIN, player, event.getTarget().getName(), "");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSwitch(ServerSwitchEvent event) {
        if (event.getFrom() == null)
            return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_SWITCH))
            return;

        RyMessageUtils.broadcast(player, Permissions.STAFFCHAT_SEE, plugin.getConfig().SWITCH_FORMAT
                .replace("%player%", player.getName())
                .replace("%server%", player.getServer().getInfo().getName())
                .replace("%from%", event.getFrom().getName()));

        RyMessageUtils.sendConsole(true, plugin.getConfig().SWITCH_FORMAT
                .replace("%player%", player.getName())
                .replace("%server%", player.getServer().getInfo().getName())
                .replace("%from%", event.getFrom().getName()));

        DiscordImpl.sendJoin(JoinType.SWITCH, player, player.getServer().getInfo().getName(), event.getFrom().getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT))
            return;
        if (player.getServer() == null)
            return;

        RyMessageUtils.broadcast(player, Permissions.STAFFCHAT_SEE, plugin.getConfig().QUIT_FORMAT
                .replace("%player%", player.getName())
                .replace("%server%", player.getServer().getInfo().getName()));

        RyMessageUtils.sendConsole(true, plugin.getConfig().QUIT_FORMAT
                .replace("%player%", player.getName())
                .replace("%server%", player.getServer().getInfo().getName()));

        DiscordImpl.sendJoin(JoinType.LEAVE, player, player.getServer().getInfo().getName(), "");
    }
}