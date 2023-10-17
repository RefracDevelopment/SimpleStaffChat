package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
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
        if (!player.hasPermission(plugin.getPermissions().STAFFCHAT_JOIN)) return;

        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                plugin.getColor().sendCustomMessage(p, plugin.getColor().translate(player, plugin.getSettings().JOIN_FORMAT));
            }
        }
        if (plugin.getSettings().BUNGEECORD) {
            plugin.getPluginMessage().sendStaffChat(player, plugin.getColor().translate(player, plugin.getSettings().JOIN_FORMAT));
        }
        plugin.getColor().log2(plugin.getColor().translate(player, plugin.getSettings().JOIN_FORMAT));
        plugin.getDiscordImpl().sendJoin(player, JoinType.JOIN, plugin.getSettings().JOIN_FORMAT
                .replace("%server%", plugin.getSettings().SERVER_NAME)
                .replace("%player%", player.getName())
        );
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        

        if (!plugin.getSettings().JOIN_ENABLED) return;
        if (!player.hasPermission(plugin.getPermissions().STAFFCHAT_QUIT)) return;

        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                plugin.getColor().sendCustomMessage(p, plugin.getColor().translate(player, plugin.getSettings().QUIT_FORMAT));
            }
        }
        if (plugin.getSettings().BUNGEECORD) {
            plugin.getPluginMessage().sendStaffChat(player, plugin.getColor().translate(player, plugin.getSettings().QUIT_FORMAT));
        }
        plugin.getColor().log2(plugin.getColor().translate(player, plugin.getSettings().QUIT_FORMAT));
        plugin.getDiscordImpl().sendJoin(player, JoinType.LEAVE, plugin.getSettings().QUIT_FORMAT
                .replace("%server%", plugin.getSettings().SERVER_NAME)
                .replace("%player%", player.getName())
        );
    }

    private void sendDevMessage(Player player) {
        

        plugin.getColor().sendCustomMessage(player, " ");
        plugin.getColor().sendCustomMessage(player, "&aWelcome " + plugin.getDescription().getName() + " Developer!");
        plugin.getColor().sendCustomMessage(player, "&aThis server is currently running " + plugin.getDescription().getName() + " &bv" + plugin.getDescription().getVersion() + "&a.");
        plugin.getColor().sendCustomMessage(player, "&aPlugin name&7: &f" + plugin.getDescription().getName());
        plugin.getColor().sendCustomMessage(player, " ");
        plugin.getColor().sendCustomMessage(player, "&aServer version&7: &f" + plugin.getServer().getVersion());
        plugin.getColor().sendCustomMessage(player, " ");
    }
}