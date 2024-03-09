package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getUniqueId().equals(getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(getDevUUID2)) {
            sendDevMessage(player);
        }

        if (!plugin.getSettings().JOIN_ENABLED) return;
        if (!player.hasPermission(Permissions.STAFFCHAT_JOIN)) return;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendCustomMessage(p, Color.translate(player, plugin.getSettings().JOIN_FORMAT));
            }
        }
        if (plugin.getSettings().BUNGEECORD) {
            plugin.getPluginMessage().sendStaffChat(Color.translate(player, plugin.getSettings().JOIN_FORMAT));
        }
        Color.log2(Color.translate(player, plugin.getSettings().JOIN_FORMAT));
        DiscordImpl.sendJoin(JoinType.JOIN, player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getSettings().JOIN_ENABLED) return;
        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT)) return;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendCustomMessage(p, Color.translate(player, plugin.getSettings().QUIT_FORMAT));
            }
        }
        if (plugin.getSettings().BUNGEECORD) {
            plugin.getPluginMessage().sendStaffChat(Color.translate(player, plugin.getSettings().QUIT_FORMAT));
        }
        Color.log2(Color.translate(player, plugin.getSettings().QUIT_FORMAT));
        DiscordImpl.sendJoin(JoinType.LEAVE, player);
    }

    private void sendDevMessage(Player player) {
        Color.sendCustomMessage(player, " ");
        Color.sendCustomMessage(player, "&aWelcome " + plugin.getDescription().getName() + " Developer!");
        Color.sendCustomMessage(player, "&aThis server is currently running " + plugin.getDescription().getName() + " &bv" + plugin.getDescription().getVersion() + "&a.");
        Color.sendCustomMessage(player, "&aPlugin name&7: &f" + plugin.getDescription().getName());
        Color.sendCustomMessage(player, " ");
        Color.sendCustomMessage(player, "&aServer version&7: &f" + Bukkit.getVersion());
        Color.sendCustomMessage(player, " ");
    }
}