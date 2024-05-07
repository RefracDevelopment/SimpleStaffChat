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

    protected final UUID getDevUUID = UUID.fromString("d9c670ed-d7d5-45fb-a144-8b8be86c4a2d");
    protected final UUID getDevUUID2 = UUID.fromString("ab898e40-9088-45eb-9d69-e0b78e872627");
    private final SimpleStaffChat plugin;

    public JoinListener(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(ServerConnectEvent event) {
        if (event.getReason() != ServerConnectEvent.Reason.JOIN_PROXY)
            return;

        ProxiedPlayer player = event.getPlayer();

        if (player.getUniqueId().equals(getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(getDevUUID2)) {
            sendDevMessage(player);
        }

        if (!player.hasPermission(Permissions.STAFFCHAT_JOIN))
            return;

        RyMessageUtils.broadcast(player, Permissions.STAFFCHAT_SEE, plugin.getConfig().JOIN_FORMAT
                .replace("%server%", event.getTarget().getName()));

        RyMessageUtils.sendConsole(true, plugin.getConfig().JOIN_FORMAT
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

        RyMessageUtils.broadcast(player, plugin.getConfig().SWITCH_FORMAT
                .replace("%server%", player.getServer().getInfo().getName())
                .replace("%from%", event.getFrom().getName()));

        RyMessageUtils.sendConsole(true, plugin.getConfig().SWITCH_FORMAT
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

        RyMessageUtils.broadcast(player, plugin.getConfig().QUIT_FORMAT
                .replace("%server%", player.getServer().getInfo().getName()));

        RyMessageUtils.sendConsole(true, plugin.getConfig().QUIT_FORMAT
                .replace("%server%", player.getServer().getInfo().getName()));
        DiscordImpl.sendJoin(JoinType.LEAVE, player, player.getServer().getInfo().getName(), "");
    }

    private void sendDevMessage(ProxiedPlayer player) {
        RyMessageUtils.sendPlayer(player, " ");
        RyMessageUtils.sendPlayer(player, "&aWelcome " + plugin.getDescription().getName() + " Developer!");
        RyMessageUtils.sendPlayer(player, "&aThis server is currently running " + plugin.getDescription().getName() + " &bv" + plugin.getDescription().getVersion() + "&a.");
        RyMessageUtils.sendPlayer(player, "&aPlugin name&7: &f" + plugin.getDescription().getName());
        RyMessageUtils.sendPlayer(player, " ");
        RyMessageUtils.sendPlayer(player, "&aServer version&7: &f" + plugin.getProxy().getVersion());
        RyMessageUtils.sendPlayer(player, " ");
    }
}