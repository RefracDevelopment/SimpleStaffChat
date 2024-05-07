package me.refracdevelopment.simplestaffchat.listeners;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.utilities.JoinType;
import me.refracdevelopment.simplestaffchat.utilities.Permissions;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;


public class JoinListener implements Listener {

    private final SimpleStaffChat plugin;
    private final UUID getDevUUID = UUID.fromString("d9c670ed-d7d5-45fb-a144-8b8be86c4a2d");
    private final UUID getDevUUID2 = UUID.fromString("ab898e40-9088-45eb-9d69-e0b78e872627");

    public JoinListener(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getUniqueId().equals(this.getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(this.getDevUUID2)) {
            sendDevMessage(player);
        }

        if (!player.hasPermission(Permissions.STAFFCHAT_JOIN))
            return;

        RyMessageUtils.broadcast(player, Permissions.STAFFCHAT_SEE, this.plugin.getSettings().JOIN_FORMAT);

        RyMessageUtils.sendConsole(true, this.plugin.getSettings().JOIN_FORMAT);
        DiscordImpl.sendJoin(JoinType.JOIN, player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT))
            return;

        RyMessageUtils.broadcast(player, Permissions.STAFFCHAT_SEE, this.plugin.getSettings().QUIT_FORMAT);

        RyMessageUtils.sendConsole(true, this.plugin.getSettings().QUIT_FORMAT);
        DiscordImpl.sendJoin(JoinType.LEAVE, player);
    }

    private void sendDevMessage(Player player) {
        RyMessageUtils.sendPlayer(player, " ");
        RyMessageUtils.sendPlayer(player, "&aWelcome " + this.plugin.getDescription().getName() + " Developer!");
        RyMessageUtils.sendPlayer(player, "&aThis server is currently running " + this.plugin.getDescription().getName() + " &bv" + this.plugin.getDescription().getVersion() + "&a.");
        RyMessageUtils.sendPlayer(player, "&aPlugin name&7: &f" + this.plugin.getDescription().getName());
        RyMessageUtils.sendPlayer(player, " ");
        RyMessageUtils.sendPlayer(player, "&aServer version&7: &f" + Bukkit.getVersion());
        RyMessageUtils.sendPlayer(player, " ");
    }
}